<?php

/**
 *  Calls the request and returns the request
 */
// spl_autoload_extensions(".php");
// spl_autoload_register();
///require_once("RequestHandlers/Contact.php");

namespace Everyman\Neo4j;
require("phar://neo4jphp.phar");

require_once "RequestHandlers\Contact.php";
require_once "RequestHandlers\Note.php";
echo "WoAH DUDE\n";

class Controller {
    
    public static function parse($class, $id, $type, $postContent){
    
        $aClient = new Client();
     
        $ch = new Contact($aClient);
        $getContactResult = $ch->GET(431);
        if (!$getContactResult) {echo "GET CONTACT WAS FALSE";} else {
            echo json_encode($getContactResult);
        }

        $nh = new Note($aClient);
        $getNoteResult =  $nh->GET(430);
        if (!$getNoteResult) {echo "GET NOTE WAS FALSE";} else {
            echo json_encode($getNoteResult);
        }
        
        /*
        //parse normal request
        if(class_exists($class)){
            $instnace = new $class(new Client);
            //if id==null, assume /Class/ so PUT or POST and pass postcontent
            //if id!=null, assume /Class/# so GET or DELETE and pass id
            echo $instnace.$type($id==null ? $postContent : $id);
        }else{
            echo "Error: file not recognized";
        }
        */
         
    }
    public static function parseSpecial($class1, $class2, $id1, $id2, $type, $postContent){
        //parse special request
        
    }        
    /*
    *This function initializes the headers needed for the application
    */
    public static function initHeaders(){
            define("PBKDF2_HASH_ALGORITHM", "sha256");
            define("PBKDF2_ITERATIONS", 1000);
            define("PBKDF2_SALT_BYTE_SIZE", 24);
            define("PBKDF2_HASH_BYTE_SIZE", 24);

            define("HASH_SECTIONS", 4);
            define("HASH_ALGORITHM_INDEX", 0);
            define("HASH_ITERATION_INDEX", 1);
            define("HASH_SALT_INDEX", 2);
            define("HASH_PBKDF2_INDEX", 3);

                    if (isset($_SERVER['HTTP_ORIGIN'])) {
                            header("Access-Control-Allow-Origin: *");
                            header('Access-Control-Allow-Credentials: true');
                            header('Access-Control-Max-Age: 86400');    // cache for 1 day
                    }

                    // Access-Control headers are received during OPTIONS requests
                    if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
                            if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD']))
                                    header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");         

                            if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']))
                                    header("Access-Control-Allow-Headers: GET, POST, PUT, DELETE, OPTIONS");
                            exit(0);
                    }
    }
}


Controller::initHeaders();

if (isset($_REQUEST['class2'])) {
    Controller::parseSpecial($_GET['class1'].'Handler', 
                            $_GET['class2'], 
                            $_GET['id1'], 
                            $_GET['id2'],
                            $_SERVER['REQUEST_METHOD'], 
                            json_decode(file_get_contents('php://input')));
} else {
    Controller::parse($_GET['class1'],//.'Handler', 
                    (isset($_GET['id1']) ? $_GET['id1'] : null), 
                    $_SERVER['REQUEST_METHOD'], 
                    json_decode(file_get_contents('php://input')));
}

