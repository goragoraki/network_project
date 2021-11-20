var zmq = require('zeromq');

console.log("Collecting updates from weather server...");

var subscriber = zmq.socket('sub');
subscriber.connect("tcp://localhost:5556");

var filter = null;
if (process.argv.length > 2) {
  filter = process.argv[2];
} else {
  filter = "10001";
}
console.log(filter);
subscriber.subscribe(filter);

var total_temp = 0
    , temps = 0;
var cnt = 0;
subscriber.on('message', function (data) {

  var pieces      = data.toString().split(" ")
    , zipcode     = parseInt(pieces[0], 10)
    , temperature = parseInt(pieces[1], 10)
    , relhumidity = parseInt(pieces[2], 10);
  temps += 1;
  total_temp += temperature;
  cnt += 1
  if (temps <= 30 && cnt <=20) {
    console.log([
      "Average temperature for zipcode '",
      filter,
      "' was ",
      (total_temp / temps).toFixed(2),
      " F"].join(""));
    total_temp = 0;
      temps = 0;
      if (cnt === 20) { console.log("completed programs") };
  }
});

