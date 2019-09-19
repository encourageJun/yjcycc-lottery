var IDict = {};

IDict.map = {};

IDict.init = false;

IDict.load = function (typeName) {
    $.ajax({
        type: "GET",
        url: "/dict/listdata",
        data: {
            type: typeName
        },
        async: false,
        dataType: "json",
        success: function (data) {
            if (data.code != 200) {
                console.log("load dict failed:" + data.msg + ", type:" + typeName);
                IDict.map[typeName] = {};
                return;
            }
            data = data.list;
            if (!data || data.length == 0) {
                console.log("load dict failed:" + typeName);
                IDict.map[typeName] = {};
                return;
            }
            for (let i = 0; i < data.length; i++) {
                if (!IDict.map[typeName]) {
                    IDict.map[typeName] = {};
                }
                IDict.map[typeName][data[i].value] = data[i].name;
            }
            IDict.init = true;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("you must login authority");
        }
    });
};

IDict.getLabel = function (type, value, def) {
    if (!IDict.map[type]) {
        IDict.load(type);
    }
    if (IDict.map[type] && IDict.map[type][value]) {
        return IDict.map[type][value];
    }
    return def;
};