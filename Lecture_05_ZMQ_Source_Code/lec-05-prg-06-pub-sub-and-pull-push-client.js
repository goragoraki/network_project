// subber.js
var zmq = require("zeromq"),
    sock = zmq.socket("sub"),
    pusher = zmq.socket("push");

sock.connect("tcp://127.0.0.1:3000");
pusher.connect("tcp://127.0.0.1:3001");
sock.subscribe("kitty cats");
console.log("Subscriber connected to port 3000");

sock.on("message", function(topic, sm) {
    console.log(
        "I received a message ", sm.toString());
});

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //최댓값은 제외, 최솟값은 포함
  }

setInterval(function () {
    rnum = getRandomInt(1, 100)
    if (rnum < 10) {
        console.log("I: sending message ", rnum);
        pusher.send(rnum);
    }
}, 100)