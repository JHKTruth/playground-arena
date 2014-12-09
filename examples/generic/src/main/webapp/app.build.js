({
    appDir : "./",
    baseUrl : "js/",
    dir : "../../../target/jpa-generic-examples",
    //Comment out the optimize line if you want
    //the code minified by UglifyJS
    //optimize : "none",	//none | uglify [default] | closure [when using Java]
    
    paths : {
        "jquery": "require-jquery"
    },
    
    modules : [
        //Optimize the require-jquery.js file by applying any minification
        //that is desired via the optimize: setting above.
        {
            name : "require-jquery"
        },
        
        //Optimize the application files. Exclude jQuery since it is
        //included already in require-jquery.js
        {
            name : "main",
            exclude : [ "jquery" ]
        },
        
        {
            name : "cassandraMain",
            exclude : [ "jquery" ]
        },
        
        {
            name : "hadoopMain",
            exclude : [ "jquery" ]
        },
        
        {
            name : "mongoDBMain",
            exclude : [ "jquery" ]
        },
        
        {
            name : "mySQLMain",
            exclude : [ "jquery" ]
        }
    ]
})
