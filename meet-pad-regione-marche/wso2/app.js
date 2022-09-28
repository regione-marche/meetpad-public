var http		= require('http');
var httpProxy	= require('http-proxy');
var proxy		= httpProxy.createProxyServer({});

var server		= http.createServer(function(req, res) {

  // se è stato specificato il token sulla request provo ad usare questo
  try{
    let authToken = req.headers.authorization.split(".")[1];
    //console.info(authToken);

    let header = JSON.parse(Buffer.from(authToken, 'base64').toString());
    //console.info(header.sub);
	
    let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":header.sub}) ).toString('base64')
    console.info(appIdToken);
    req.headers["App-Id-Token"] = appIdToken;
  }catch(error){
    //console.error(error)
  }

  // se non è stato passato nessun token sulla request allora lo specifico io a mano
  // sotto sono elencati una serie di cf di utenti, sostituire il cf con quello che si intende usare
  try{
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"GRNKJS73D56G479J"}) ).toString('base64')	//302-Granci
  
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"TZRVNI79P58G113H"}) ).toString('base64')	
    //let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"CRCNDR64A21E388U"}) ).toString('base64')

  //let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"FRSFNC77R44E388W"}) ).toString('base64')
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"MDRMTT79B19D451J"}) ).toString('base64')	// medardoni
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"CNNMHL69P07E885K"}) ).toString('base64')	//303-Cannito	
    //let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"RSNNDR74E11L719I"}) ).toString('base64')
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"DNLFRC88H21G716U"}) ).toString('base64')
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"DLBDNL67T65A271P"}) ).toString('base64')	// delbello
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"CRCPLA75H65E783K"}) ).toString('base64')	// generic USR users
	//let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"SPRCSR56E16E783Q"}) ).toString('base64')	// generic USR users
   	let appIdToken = "1."+Buffer.from( JSON.stringify({"sub":"CLALSN78L69G113T"}) ).toString('base64')

    req.headers["App-Id-Token"] = appIdToken;
    console.log("TOKEN: " + appIdToken)
  }catch(error){
      console.error(error)
  }
  
  // Inoltro la richiesta con l'header aggiuntivo al backend
  // Attenzione! Usare host e porta in uso sul proprio jboss
  try{
    proxy.web(req, res, { target: 'http://localhost:8080' });
    //proxy.web(req, res, { target: 'http://localhost:8080/conferenza/' });
  }catch(err){
    console.log(error)
  }
  
});

console.log("listening on port 5050")
server.listen(5050);



//"mobile-app-distribution": "https://github.com/ruddenchaux/mobile-app-distribution.git",
