-- 初始化业务数据
truncate bs_open_number;
select * from bs_open_number;

truncate bs_open_number_ext;
select * from bs_open_number_ext;

truncate bs_order;
select * from bs_order;

truncate bs_plan;
select * from bs_plan;

truncate bs_plan_history;
select * from bs_plan_history;

truncate bs_user_balance_record;
select * from bs_user_balance_record;

update bs_user_balance set balance = 4000 where id = 1;
select * from bs_user_balance;

update bs_plan_config set is_occupy = 1 where is_occupy <> 1;

-- -------------------------------------------------------------------------
select * from bs_plan_config;
select * from bs_pursue_scheme;
select * from bs_play_category;
select * from sys_parameter;

-- 计划历史记录
create table bs_plan_history
(
    id bigint auto_increment comment 'id' primary key,
    plan_config_id    bigint                      null comment '投注计划配置id',
    dict_lottery_type varchar(30)    default ''   null comment '彩票种类(字典标签label)',
    status            smallint(1)    default 0    not null comment '中奖状态, 字典:plan_status, 0追号中 1未中奖 2已中奖',
    stage_count       smallint(2)    default 0    not null comment '当前投注期数',
    total_stage_count smallint(2)    default 0    not null comment '总期数',
    amount            decimal(10, 2) default 0.00 null comment '当期投注金额',
    double_count      smallint(6)    default 0    null comment '当期投注倍数',
    total_amount      decimal(10, 2) default 0.00 null comment '累积投注金额',
    win_amount        decimal(10, 2) default 0.00 null comment '中奖金额',
    profit_amount     decimal(10, 2) default 0.00 null comment '盈利金额',
    execute_index     int(10)        default 0    null comment '计划配置执行序号, 例如: 0,1,2,3..., 持续累加, 已有计划数据的情况下不可清零.(来自计划配置的executeIndex)',
    create_time       datetime                    not null comment '创建时间',
    update_time       datetime                    null comment '更新时间'
) comment '投注计划' charset = utf8;



