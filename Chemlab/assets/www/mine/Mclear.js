var $ = function (o) { return document.getElementById(o); }

var canvas = $("canvas");
var context = canvas.getContext("2d");
//网格
var canGrid;
//方块
var squGrid;

//方块列表
var mapList = [];

//图片
var img;

//场景宽度
var stageWidth = 300;
//场景高度
var stageHeight = 400;
//游戏难度
var gameDifficulty = '简单';

//浏览器属性
var moz = {
    width: function () { return document.documentElement.clientWidth; },
    height: function () { return document.documentElement.clientHeight; }
}
//临时坐标
var coorRecord = null;
//发现的地雷数
var mineNum_Seek = 0;
//已标记数
var yetSign = 0;
//地雷数
var minesNum = 0;
//过去的时间
var formTime = 0;
//游戏状态
var gameState = false;
//需要加载的图像
var imageAddress = ['images/restart.png', 'images/bg2.png'];
//已加载好的图像
var imageStart = [];
//当前加载的图像ID
var imageId = 0;

(function loadImag() {
    var imageObj = new Image();
    imageObj.src = imageAddress[imageId];
    imageObj.onload = function () {
        imageStart.push(imageObj);
        imageId++;
        if (imageId < imageAddress.length) {
            loadImag();
        } else {
            //$('load').style.display = 'none';
            //$('Difficu').style.display = 'block';
        }
    }
})()
//设置场景宽度
function setWidth(width, height) {
    stageWidth = width;
    stageHeight = height;
    var width = width || stageWidth;
    var height = height || stageHeight;
    canvas.width = width;
    canvas.height = height;
    $('Mclear').style.width = width + 'px';
    $('restart').style.margin = '0 ' + (width - 200) / 2 + 'px';
}
//初始化
function initMian() {
    //网格
    canGrid=null;
    //方块
    squGrid = null;
    //方块列表
    mapList.length = 0;
    //临时坐标
    coorRecord = null;
    //发现的地雷数
     mineNum_Seek = 0;
    //已标记数
    yetSign = 0;
    //地雷数
    minesNum = 0;
    //过去的时间
    formTime = 0;
    //游戏状态
    gameState = false;
}
//难度选择
function Difficulty(Diff) {
    //$('hint').className = 'animati1';

    initMian();

    gameDifficulty = Diff;

    mapInit();
}
//初始化地图
function mapInit() {
    img = imageStart[0];

    GenerateMap();
}
//生成地图
function GenerateMap() {
    var listNum;
    switch (gameDifficulty) {
        case '简单': listNum = 10; minesNum = 20; break;
        case '一般': listNum = 20; minesNum = 50; break;
        case '困难': listNum = 30; minesNum = 100; break;
    }

    var widht = listNum * 30;
    var cHeighe = moz.height();
    var height = widht > cHeighe ? cHeighe - 150 : widht;
    height = height - height % 30;
    setWidth(widht, height);

    var listNum_y = height / 30;

    GenerateGrid(listNum, listNum_y, widht, height);

    GenerateSquare(listNum, listNum_y, minesNum, widht, height);

    addNum(listNum, listNum_y);

    gameState = true;

    signResidue();

    TimeResidue();
}
document.oncontextmenu = function () {
    if (window.navigator.userAgent.toLowerCase().indexOf("ie") == -1) { return false; }
    window.event.returnValue = false;
}
document.onselectstart = function () { return false; }
document.onmousedown = mouseDown;
document.onmouseup = mouseUp;
/* $('butt').onclick = function () {
    $('text').style.display = 'none';
    $('Difficu').style.display = 'block';
}
$('restart').onclick = function () {
    $('text').style.display = 'none';
    $('Difficu').style.display = 'block';
    GameOver();
} */
//鼠标按下
function mouseDown(e) {

    var sc = getSomeCoord(e);
    if (!sc || !gameState || !mapList[sc.x][sc.y].There) return;

    coorRecord = { x: sc.x, y: sc.y };

    restore();
}
//鼠标抬起
function mouseUp(e) {
    if (coorRecord == null) return;

    var sc = getSomeCoord(e);
    if (!sc) return refreScene();

    var squText = squGrid.getContext('2d');

    if (e.button == '0') {
        if (sc.x == coorRecord.x && sc.y == coorRecord.y && !mapList[coorRecord.x][coorRecord.y].sign) {
            var num = mapList[coorRecord.x][coorRecord.y].num;
            if (typeof num == 'undefined') {

                mapList[coorRecord.x][coorRecord.y].There = false;
                squText.clearRect(coorRecord.y * 30, coorRecord.x * 30, 30, 30);
                squText.fillStyle = "#c30700";
                squText.fillRect(coorRecord.y * 30, coorRecord.x * 30, 30, 30);

                squText.drawImage(img, 240, 144, 30, 30, coorRecord.y * 30, coorRecord.x * 30, 30, 30);

                GenerateMine(squText, mapList[coorRecord.x][coorRecord.y]);

            } else if (num == 0) {

                undefiAdjac(squText);

            } else if (num > 0) {
                GenerateNum(squText, num);
            }
        }
    } else if (e.button == '2') {
        if (yetSign < minesNum && !mapList[coorRecord.x][coorRecord.y].sign) {

            return rightClick(squText, sc.x, sc.y);

        } else if (mapList[coorRecord.x][coorRecord.y].sign) {

            return clearSign(squText, sc.x, sc.y);

        }
    }

    coorRecord = null;
    refreScene();
}
//还原状态
function restore() {
    var x = coorRecord.x;
    var y = coorRecord.y;

    if (mapList[x][y].sign)
        context.drawImage(img, 90, 114, 30, 30, y * 30, x * 30, 30, 30);
    else
        context.drawImage(img, 60, 114, 30, 30, y * 30, x * 30, 30, 30);
}
//绘制地雷
function GenerateMine(squText, mines) {
    for (var i = 0; i < mapList.length; i++) {
        for (var z = 0; z < mapList[i].length; z++) {
            if (typeof mapList[i][z].num == 'undefined' && mapList[i][z] != mines) {

                mapList[i][z].There = false;
                squText.clearRect(z * 30, i * 30, 30, 30);
                squText.drawImage(img, 240, 144, 30, 30, z * 30, i * 30, 30, 30);
            }
        }
    }
    return GameOver(0);
}
//绘制数字
function GenerateNum(squText, num) {
    mapList[coorRecord.x][coorRecord.y].There = false;
    squText.clearRect(coorRecord.y * 30, coorRecord.x * 30, 30, 30);
    squText.drawImage(img, (num - 1) * 30, 144, 30, 30, coorRecord.y * 30, coorRecord.x * 30, 30, 30);
}
//刷新场景
function refreScene() {
    signResidue();

    context.clearRect(0, 0, stageWidth, stageHeight);
    context.drawImage(canGrid, 0, 0, stageWidth, stageHeight);
    context.drawImage(squGrid, 0, 0, stageWidth, stageHeight);
}
//更新标记剩余数
function signResidue() {

    var sign = $('tabnum').getElementsByTagName('span');
    for (var i = 0; i < 3; i++) {
        sign[i].style.backgroundPositionX = '0px';
    }

    var num = (minesNum - yetSign).toString();
    for (var i = num.length, z = 2; i > 0; i--, z--) {
        sign[z > 2 ? 2 : z].style.backgroundPositionX = -(parseInt(num.substring(i - 1, i)) * 20) + 'px';
    }
}
//更新时间
function TimeResidue() {
    if (!gameState) return;
    if (formTime == 999) return GameOver(0);
    formTime++;

    var time = $('time').getElementsByTagName('span');
    for (var i = 0; i < 3; i++) {
        time[i].style.backgroundPositionX = '0px';
    }

    var num = formTime.toString();
    for (var i = num.length, z = 2; i > 0; i--, z--) {
        time[z > 2 ? 2 : z].style.backgroundPositionX = -(parseInt(num.substring(i - 1, i)) * 20) + 'px';
    }
    setTimeout(TimeResidue, 1000);
}
//绘制标记
function rightClick(squText, x, y) {

    yetSign++;

    if (typeof mapList[x][y].num == 'undefined') mineNum_Seek++;

    mapList[x][y].sign = true;
    squText.clearRect(y * 30, x * 30, 30, 30);
    squText.drawImage(img, 30, 114, 30, 30, y * 30, x * 30, 30, 30);
    coorRecord = null;
    refreScene();
    if (mineNum_Seek == minesNum) GameOver(1);
}
//清除标记
function clearSign(squText, x, y) {

    yetSign--;

    if (typeof mapList[x][y].num == 'undefined') mineNum_Seek--;

    mapList[x][y].sign = false;
    squText.clearRect(y * 30, x * 30, 30, 30);
    squText.drawImage(img, 0, 114, 30, 30, y * 30, x * 30, 30, 30);
    coorRecord = null;
    refreScene();
}
//获取点击位置的横竖坐标
function getSomeCoord(e) {
    var e = e || window.event;
    var x = e.pageY - $('shadow').offsetTop;
    var y = e.pageX - $('shadow').offsetLeft;
    if (x < 0 || y < 0 || y > stageWidth || x > stageHeight) return false;
    x = parseInt(x / 30);
    y = parseInt(y / 30);
    return { x: x, y: y }
}
//返回相邻空格
function undefiAdjac(squText) {

    mapList[coorRecord.x][coorRecord.y].There = false;
    squText.clearRect(coorRecord.y * 30, coorRecord.x * 30, 30, 30);

    var map = [];
    map.push(coorRecord);
    for (var z = 0; z < map.length; z++) {

        var list = map[z].y;//列
        var line = map[z].x;//行
        var X8 = [
            [line - 1, list],
            [line, list - 1],
            [line, list + 1],
            [line + 1, list],
            [line - 1, list - 1],
            [line - 1, list + 1],
            [line + 1, list - 1],
            [line + 1, list + 1]
        ];
        for (var i = 0; i < 8; i++) {
            line = X8[i][0];
            list = X8[i][1];
            if (line < 0 || list < 0 || list > stageWidth / 30 - 1 || line > stageHeight / 30 - 1 || mapList[line][list].There == false) continue;

            if (mapList[line][list].num == 0) {

                map.push({ x: line, y: list });
                mapList[line][list].There = false;
                squText.clearRect(list * 30, line * 30, 30, 30);
            }
            else if (!isNaN(mapList[line][list].num)) {
                coorRecord = { x: line, y: list };
                GenerateNum(squText, mapList[line][list].num);
            }
        }
    }
}
//添加数字标记
function addNum(Lx, Ly) {
    var num = 0;
    for (var i = 0; i < Ly; i++) {
        for (var z = 0; z < Lx; z++) {
            if (mapList[i][z].mine) continue;
            var list = mapList[i][z].x / 30;//列
            var line = mapList[i][z].y / 30;//行

            var X8 = [
                [line - 1, list],
                [line, list - 1],
                [line, list + 1],
                [line + 1, list],
                [line - 1, list - 1],
                [line - 1, list + 1],
                [line + 1, list - 1],
                [line + 1, list + 1]
            ];
            for (var j = 0; j < 8; j++) {
                line = X8[j][0];
                list = X8[j][1];
                if (line < 0 || list < 0 || line > Ly - 1 || list > Lx - 1) continue;
                if (mapList[line][list].mine) {
                    num++;
                }
            }
            mapList[i][z].num = num;
            num = 0;
        }
    }
}
//获取指定范围的随机数
function randomNum(min, max, num) {
    var ranNum = [];
    for (var i = 0; i < num; i++) {
        var Num = min + Math.round(Math.random() * (max - min));
        if (repeat(ranNum, Num)) {
            i--;
            continue;
        }
        ranNum.push(Num);
    }
    return sorting(ranNum);
}
//判断是否有重复
function repeat(ranNum, Num) {
    for (var i = 0; i < ranNum.length; i++) {
        if (ranNum[i] == Num) {
            return true;
        }
    }
}
//排序
function sorting(array) {
    var bool;
    for (var i = 0; i < array.length; i++) {
        if (array[i] > array[i + 1]) {
            bool = true;
            var arr = array[i];
            array[i] = array[i + 1];
            array[i + 1] = arr;
        }
    }
    return bool ? sorting(array) : array;
}
//添加canvas
function addCanvas(widht, height) {
    var can = document.createElement('canvas');
    can.width = widht;
    can.height = height;
    return can;
}
//绘制方块
function GenerateSquare(listNum, listNum_y, minesNum, widht, height) {
    squGrid = addCanvas(widht, height);
    var squText = squGrid.getContext('2d');

    var mpl = randomNum(0, listNum * listNum_y, minesNum);
    for (var i = 0; i < listNum_y; i++) {
        mapList[i] = [];
        for (var j = 0; j < listNum; j++) {
            var x = j * 30;
            var y = i * 30;
            var mine = false;
            if (mpl[0] == i * listNum + j) {
                mine = true;
                mpl.splice(0, 1);
            }
            var square = { x: x, y: y, mine: mine, There: true, sign: false };

            mapList[i][j] = square;

            squText.drawImage(img, 0, 114, 30, 30, x, y, 30, 30);
        }
    }
    context.drawImage(squGrid, 0, 0);
}
//绘制网格
function GenerateGrid(listNum, listNum_y, widht, height) {
    canGrid = addCanvas(widht, height);
    var GridText = canGrid.getContext('2d');

    for (var i = 1; i < listNum; i++) {
        GridText.beginPath();
        GridText.lineWidth = 0.1;
        GridText.fillStyle = '#bcbbbb';
        GridText.moveTo(i * 30, 0);
        GridText.lineTo(i * 30, height);
        GridText.stroke();
    }
    for (var i = 1; i < listNum_y; i++) {
        GridText.beginPath();
        GridText.lineWidth = 0.1;
        GridText.moveTo(0, i * 30);
        GridText.lineTo(widht, i * 30);
        GridText.stroke();
    }
    context.drawImage(canGrid, 0, 0);
}
//游戏结束
function GameOver(num) {
    gameState = false;
    if (num == 0) {
        if (confirm("失败了，再来一次！")==true){
			Difficulty(gameDifficulty);
		}
    } else if (num == 1) {
        if (confirm("胜利了，来点高难度的！")==true){
			Difficulty(gameDifficulty);
		}
    }
}

