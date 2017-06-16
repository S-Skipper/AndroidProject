window.requestAnimFrame =
    window.requestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    function(f) {
        setTimeout(f, 1000 / 30);
    };

var c = document.getElementById('canv');
c.width = window.innerWidth;
c.height = window.innerHeight;

var $ = c.getContext('2d');

w = c.width;
h = c.height;

function draw(ang, dist) {
    //number of vortext rainbow rings
    var rings = 15;
    //used as a color variable below. Change it to easily play with color tone
    var col = 16;
    for (var u = 0; u < rings; u++) {
        //space between rings
        space = Math.pow(1.5, (u + 1))
        sd = space + dist * space;
        //wave action
        x = w / 2 + Math.cos(ang) * sd;
        y = h / 2 + Math.sin(ang) * sd;
        //size of each ring (change the 30 to adjust: 
        //higher = smaller, lower = larger)
        s = sd / 10;
        //xor effect
        $.globalCompositeOperation = 'xor';
        $.fillStyle = "hsla(" + (u / col * 300) + ",100%, 50%, 1)"
        $.beginPath();
        $.arc(x, y, s * 3, 0, 2 * Math.PI, false);
        $.fill();
    }
}
a = 0;

function go() {
    c.width = c.width;
    //angle var
    a++;
    //num of circles
    var circs = 40;
    //center
    var vortex = 0;

    for (var i = 0; i < circs; i++) {
        ang = a / 100 + i / circs * Math.PI * 2;
        dist = vortex + (Math.sin(3 * i / circs * Math.PI * 2) +
            1 + Math.cos(a / 20 + 2 * i / circs * Math.PI * 2) + 1) / 4;
        draw(ang, dist);
    }
    window.requestAnimFrame(go);
}
go()