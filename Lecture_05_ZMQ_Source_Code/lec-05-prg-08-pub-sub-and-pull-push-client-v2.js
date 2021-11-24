const { time } = require("console");

var zmq = require("zeromq"),
    sock = zmq.socket("sub"),
    pusher = zmq.socket("push");

sock.connect("tcp://127.0.0.1:3000");
pusher.connect("tcp://127.0.0.1:3001");
sock.subscribe("kitty cats");
console.log("Subscriber connected to port 3000");


clientID = process.argv[2];
sock.on("message", function (topic, sm) {
    msg = sm.toString();
    console.log(
        `${clientID}: receive status => ${msg}`);
});

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min;
  }

setInterval(function () {
    rnum = getRandomInt(1, 100)
  if (rnum < 10) {
    console.log(`${clientID}": send status - activated `);
    msg = "(" + clientID + ":ON)"
    pusher.send(msg);
  } else if (rnum > 90) {
    console.log(`${clientID}": send status - deactivated `);
    msg = "(" + clientID + ":OFF)"
    pusher.send(msg);
  }
}, 100)