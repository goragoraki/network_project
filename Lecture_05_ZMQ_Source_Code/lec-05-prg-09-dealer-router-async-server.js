
cluster = require('cluster')
, zmq = require('zeromq')
, backAddr  = 'tcp://127.0.0.1:12345'
, frontAddr = 'tcp://127.0.0.1:12346'
, workers = process.argv[2];

if (cluster.isWorker) {
  console.log("Worker#" + process.pid + " started");
}
function makeASocket(sockType, idPrefix, addr, bindSyncOrConnect) {
  var sock = zmq.socket(sockType)
  sock.identity = idPrefix + process.pid
  sock[bindSyncOrConnect](addr)
  return sock
}

function serverTask(){
  var backSvr = makeASocket('dealer', 'back', backAddr, 'bindSync')
  backSvr.on('message', function(){
    var args = Array.apply(null, arguments)
    frontSvr.send(args)
  })

  var frontSvr = makeASocket('router', 'front', frontAddr, 'bindSync')
  frontSvr.on('message', function(){
    var args = Array.apply(null, arguments)
    backSvr.send(args)
  })
}

function workerTask(){
  var sock = makeASocket('dealer', 'wkr', backAddr , 'connect')

	sock.on('message', function() {
    var args = Array.apply(null, arguments)
    var count = 1;
    console.log("Worker#"+process.pid+" received " + args[1] + " from " + args[2])
    sock.send(args)
	})
}
if (cluster.isMaster) {
 for (var i = 0; i < workers; i++) {
    cluster.fork({ "TYPE": 'worker'})
  }
  serverTask()
} else {
   workerTask()
}