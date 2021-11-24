var zmq = require("zeromq"),
    sock = zmq.socket("pub"),
    rcv = zmq.socket("pull");

sock.bindSync("tcp://*:3000");
rcv.bindSync("tcp://*:3001");
console.log("Publisher bound to port 3000");



rcv.on("message", function (msg) {
    console.log("I: publishing update", msg.toString());
    var sm = msg.toString();
        setTimeout(function() {
            sock.send(["kitty cats", sm]);
        }, 10);
})

