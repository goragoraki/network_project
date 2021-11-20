var zmq = require('zeromq')
  , publisher = zmq.socket('pub');

publisher.bindSync("tcp://*:5556");
console.log("Publishing updates at weather server")

function zeropad(num) {
  return num.toString().padStart(5, "0");
};

function rand(upper, extra) {
  var num = Math.abs(Math.round(Math.random() * upper));
  return num + (extra || 0);
};

while (true) {
  // Get values that will fool the boss
  var zipcode     = rand(100000)
    , temperature = rand(215, -80)
    , relhumidity = rand(50, 10)
    , update      = `${zeropad(zipcode)} ${temperature} ${relhumidity}`;
  publisher.send(update);
}
