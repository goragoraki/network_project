cluster = require('cluster')
, zmq = require('zeromq')
, frontAddr = 'tcp://127.0.0.1:12346'
, clients = 5
, workers = 2;

clientID = process.argv[2];
function makeASocket(sockType, idPrefix, addr, bindSyncOrConnect) {
  var sock = zmq.socket(sockType)
  sock.identity = idPrefix + process.pid
  sock[bindSyncOrConnect](addr)
  return sock
}

function clientTask(){
    var sock = makeASocket('dealer', 'client', frontAddr, 'connect')
    console.log(clientID);
    var count = 1;
    var interval = setInterval(function() {
        sock.send(['request #' + count, clientID]);
        console.log("Req#" + count + " sent..")
        count++;
      }, 500)

    
	sock.on('message', function(data) {
    var args = Array.apply(null, arguments)
		console.log(clientID + " received: " + args[0] + "'");
	})
}

if (cluster.isMaster) {
  for (var i = 0; i < clients; i++)  {
    cluster.fork({ "TYPE": 'client' })
  }

  cluster.on('death', function(worker) {
    console.log('worker ' + worker.pid + ' died');
  });

    clientTask()
}