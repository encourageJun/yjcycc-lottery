function changeColor(colorClass) {
    $("#chartTable .ball").attr("class", colorClass);
    localStorage.ball_color_class = colorClass;
}

function clearTable() {
    $("#chartTable tr").each(function(index) {
        if (index != 0) {
            $(this).remove();
        }
    });
}

function init(chartType) {
    let lotteryType = "f1_11x5";
    if ($("#lotteryType").val() != null && $("#lotteryType").val() != '') {
        lotteryType = $("#lotteryType").val();
    }
    $.ajax({
        url : "/chart/getOpenList",
        method : "get",
        data : {lotteryType: lotteryType},
        sync : false,
        success(data) {
            if (data) {
                $.each(data.list, function(index, item) {
                    addTr(item.stage, item.openNumber, item.number1, item.number2, item.number3, item.number4, item.number5, item.ext, chartType);
                });
                localStorage.stage = data.lastStage;
            }
        }
    });
}

function initSingle(chartType) {
    let lotteryType = "f1_11x5";
    if ($("#lotteryType").val() != null && $("#lotteryType").val() != '') {
        lotteryType = $("#lotteryType").val();
    }
    $.ajax({
        url : "/chart/getSingle",
        method : "get",
        data : {lotteryType: lotteryType},
        sync : false,
        success(data) {
            if (data) {
                console.log("local.stage: " + localStorage.stage);
                if (localStorage.stage != data.stage) {
                    console.log("start");
                    addTr(data.stage, data.openNumber, data.number1, data.number2, data.number3, data.number4, data.number5, data.ext, chartType);
                    localStorage.stage = data.stage;
                }
            }
        }
    });
}

function addTr(stage, fullNumber, num1, num2, num3, num4, num5, ext, chartType) {
    let trHtml = '';
    if ("three" == chartType) {
        fullNumber = fullNumber.substring(0,8);

        trHtml = '<tr>' +
            '<td>' + stage + '</td>' +
            '<td class="border_left border_right">' + fullNumber + '</td>' +
            fillTdOfThree("01", num1, num2, num3, ext.continuedOutCount_01) +
            fillTdOfThree("02", num1, num2, num3, ext.continuedOutCount_02) +
            fillTdOfThree("03", num1, num2, num3, ext.continuedOutCount_03) +
            fillTdOfThree("04", num1, num2, num3, ext.continuedOutCount_04) +
            fillTdOfThree("05", num1, num2, num3, ext.continuedOutCount_05) +
            fillTdOfThree("06", num1, num2, num3, ext.continuedOutCount_06) +
            fillTdOfThree("07", num1, num2, num3, ext.continuedOutCount_07) +
            fillTdOfThree("08", num1, num2, num3, ext.continuedOutCount_08) +
            fillTdOfThree("09", num1, num2, num3, ext.continuedOutCount_09) +
            fillTdOfThree("10", num1, num2, num3, ext.continuedOutCount_10) +
            fillTdOfThree("11", num1, num2, num3, ext.continuedOutCount_11) +
            '</tr>' +
            '';
    } else {

        trHtml = '<tr>' +
            '<td>' + stage + '</td>' +
            '<td class="border_left border_right">' + fullNumber + '</td>' +
            fillTdOfFive("01", num1, num2, num3, num4, num5, ext.continuedOutCount_01) +
            fillTdOfFive("02", num1, num2, num3, num4, num5, ext.continuedOutCount_02) +
            fillTdOfFive("03", num1, num2, num3, num4, num5, ext.continuedOutCount_03) +
            fillTdOfFive("04", num1, num2, num3, num4, num5, ext.continuedOutCount_04) +
            fillTdOfFive("05", num1, num2, num3, num4, num5, ext.continuedOutCount_05) +
            fillTdOfFive("06", num1, num2, num3, num4, num5, ext.continuedOutCount_06) +
            fillTdOfFive("07", num1, num2, num3, num4, num5, ext.continuedOutCount_07) +
            fillTdOfFive("08", num1, num2, num3, num4, num5, ext.continuedOutCount_08) +
            fillTdOfFive("09", num1, num2, num3, num4, num5, ext.continuedOutCount_09) +
            fillTdOfFive("10", num1, num2, num3, num4, num5, ext.continuedOutCount_10) +
            fillTdOfFive("11", num1, num2, num3, num4, num5, ext.continuedOutCount_11) +
            '</tr>' +
            '';

    }

    $("#chartTable").append(trHtml);

}

function fillTdOfThree(number, num1, num2, num3, continuedOutCount) {
    let numCol = "";
    if (num1 == number || num2 == number || num3 == number) {
        numCol = '<td><div class="'+getColorClass()+'">'+number+'</div></td>';
    } else {
        numCol = "<td>"+""+"</td>";
    }
    return numCol;
}

function fillTdOfFive(number, num1, num2, num3, num4, num5, continuedOutCount) {
    let numCol = "";
    if (num1 == number || num2 == number || num3 == number || num4 == number || num5 == number) {
        numCol = '<td><div class="'+getColorClass()+'">'+number+'</div></td>';
    } else {
        numCol = '<td><div class="ball_empty">'+continuedOutCount+'</div></td>';
    }
    return numCol;
}

function getColorClass() {
    let localColor = localStorage.ball_color_class;
    console.log("ball_color_class: " + localColor);
    if (localColor == '' || localColor == false) {
        localColor = "ball ball-bg-green";
    }
    return localColor;
}