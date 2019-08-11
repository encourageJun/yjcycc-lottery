package org.yjcycc.demo.service.zk.server;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;
import org.yjcycc.demo.service.tools.prop.RmiProperties;
import org.yjcycc.tools.common.util.SystemUtil;
import org.yjcycc.tools.zk.constant.ZkNodeConstant;
import org.yjcycc.tools.zk.model.ProducerConsumerEnum;
import org.yjcycc.tools.zk.model.UsingIpPort;
import org.yjcycc.tools.zk.model.XxNode;
import org.yjcycc.tools.zk.prop.SystemProperties;
import org.yjcycc.tools.zk.prop.ZkProperties;
import org.yjcycc.tools.zk.rmi.RMIRegister;
import org.yjcycc.tools.zk.server.AbstractZookeeperClientRegister;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

@Component
public class DemoServer extends AbstractZookeeperClientRegister implements Lifecycle, ApplicationContextAware ,InitializingBean {
	
	private static Logger logger = Logger.getLogger(DemoServer.class);
	
	private static ApplicationContext applicationContext = null;
	
	private transient boolean isRun = false;
	
	private UsingIpPort usingIpPort  = null;
	
	private int port = 0;
	
	@Override
	public String getNameSpaceForZK() {
		return ZkNodeConstant.NAME_SPACE;
	}

	@Override
	public String getNodePathForZk() {
		return ZkNodeConstant.BASE_PATH;
	}

	@Override
	public Charset getCharSetForZk() {
		return ZkNodeConstant.CHARSET;
	}

	@Override
	public String getConnectUrlForZk() {
		return ZkProperties.getInstance().getZookeeperConnUrl();
	}
	
	/**
	 * 注册rmi服务
	 * @param index
	 * @throws RemoteException
	 * @throws ExportException
	 */
	private void registRmiService(int index) throws RemoteException, ExportException {
		if(index == 0){
			//如果是重试模式，加载第0套配置
			port = RmiProperties.getInstance().getPort0();
		}else if(index == 1){
			//如果非重试模式，加载第1套配置
			port = RmiProperties.getInstance().getPort1();
		} else if(index == 2){
			//如果非重试模式，加载第2套配置
			port = RmiProperties.getInstance().getPort2();
		}  else{
			throw new IllegalArgumentException("只支持0，1，2，共3套配置！不支持：" + index);
		}
		
		// 曝露服务
		RMIRegister register = new RMIRegister(port);
//		register.regist(IDictService.class, applicationContext.getBean(IDictService.class));
//		register.regist(IDepartmentService.class, applicationContext.getBean(IDepartmentService.class));
//		register.regist(IMenuService.class, applicationContext.getBean(IMenuService.class));
//		register.regist(IRegionService.class, applicationContext.getBean(IRegionService.class));
//		register.regist(IRoleService.class, applicationContext.getBean(IRoleService.class));
//		register.regist(IUserService.class, applicationContext.getBean(IUserService.class));

		
	}

	@Override
	@PostConstruct
	public void start() {
		/**
		 * 是否使用某个自定的配置来启动，多节点分多主机部署时需要；单主机时不要配置。
		 */
		String specifyIpPortIndex = RmiProperties.getInstance().getSpecifyIpPortIndex();
		if(specifyIpPortIndex == null || "".equals(specifyIpPortIndex.trim())){
			//按照升序 读取配置文件 启动。。。。
			for(int index = 0; index < RmiProperties.MAX_NODES; index ++){
				try {
					registRmiService(index);
					initUsingIpPort();
					isRun = true;
					logger.info("DemoServer "+index+" started ok!");
					return ;
				} catch(ExportException e0){
					logger.error(e0);
					isRun = false;
				} catch (RemoteException e3) {
					logger.error(e3);
					e3.printStackTrace();
					isRun = false;
				}
			}
			logger.error("DemoServer 启动遇到问题，"+RmiProperties.MAX_NODES+"套配置没有一套有效的.....");
		}else{
			logger.info("系统配置 指定按照第 ["+specifyIpPortIndex+"] 套配置来启动：");
			try {
				int index = Integer.parseInt(specifyIpPortIndex);
				registRmiService(index);
				initUsingIpPort();
				isRun = true;
				logger.info("DemoServer "+index+" started ok!");
				return ;
			} catch(ExportException e0){
				isRun = false;
			} catch (RemoteException e3) {
				logger.error(e3);
				e3.printStackTrace();
				isRun = false;
			}
		}
	}
	
	/**
	 * 初始化uip节点信息
	 */
	private void initUsingIpPort() {
		String serverIp = SystemUtil.getHostAddress();
		int pid = SystemUtil.getPid();
		usingIpPort = new UsingIpPort(serverIp, port, pid);
		
		// 维稳监控
		System.setProperty("java.rmi.server.hostname" , serverIp);
	}

	/**
	 * 创建Zookeeper 节点树，声明当前结点是 主节点；
	 * @throws Exception 
	 */
	public boolean createTreeNode() throws Exception {
		XxNode node = new XxNode();
		node.setPcMode(ProducerConsumerEnum.PRODUCER);
		node.setEnvriment(SystemProperties.getEnviroment());
		node.setName("乐透服务");
		node.setUip(this.usingIpPort);
		//luoshan 20170503 - 灰度与流量路由
		boolean result = createTreeNode(node);
		//boolean result = zkClient.createTreeNode(this.using);		
		return result;
	}

	@Override
	@PreDestroy
	public void stop() {
		isRun = false;
	}

	@Override
	public boolean isRunning() {
		return isRun;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(applicationContext == null){
			logger.error("appContext not awared...... error");
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public UsingIpPort getUsingIpPort() {
		return usingIpPort;
	}

	public void setUsingIpPort(UsingIpPort usingIpPort) {
		this.usingIpPort = usingIpPort;
	}
	
}
