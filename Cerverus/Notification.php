<?php
/**
 * Include the API PHP file for neo4j
 */
namespace Everyman\Neo4j;
require("phar://neo4jphp.phar");


/**
 *        Create a graphDb connection 
 */
$client= new Client();

        //get the index
        $notiIndex = new Index\NodeIndex($client, 'Notifications');
        $notiIndex->save();

if(strcasecmp($_SERVER['REQUEST_METHOD'], 'POST') == 0){
	//create notification POST
        //get the json string post content
        $postContent = json_decode(@file_get_contents('php://input'));
        
        //create the node
        $notiNode= $client->makeNode();
        
        //sets the property on the node
        $notiNode->setProperty('datetime', $postContent->datetime)->setProperty('type', $postContent->type)
				->setProperty('nodeID', $notiNode->getID())
                ->setProperty('description',$postContent->description);
        
        //actually add the node in the db
        $notiNode->save();
        
        //create a relation to the user who made the meeting
        $user = $client->getNode($postContent->userID);
        $notiRel = $notiNode->relateTo($user, 'NOTIFIES')
                ->save();
        
        //add the index        
        $response= $notiIndex->add($notiNode, 'ID', $notiNode->getID());
        
        //return the created meeting id
        $array=array();
        $array['notiID']=$notiNode->getId();
        echo json_encode($array);
        
}else if(strcasecmp($_SERVER['REQUEST_METHOD'], 'GET') == 0){
        //getNotificationInfo
        $notiNode=$client->getNode($_GET['id']);
        $array=$notiNode->getProperties();
        echo json_encode($array);
}else if(strcasecmp($_SERVER['REQUEST_METHOD'], 'PUT') == 0){
        //updateNotification
        
        //get the json string post content
        $postContent = json_decode(@file_get_contents('php://input'));
        
        $noti=$client->getNode($postContent->notiID);
        if(sizeof($noti > 0)){
                if(strcasecmp($postContent->field, 'datetime') ==0){
                        $noti->setProperty('datetime', $postContent->value);
                        $noti->save();
                        $array = $noti->getProperties();
                        $array['meetingID']=$noti->getId();
                        echo json_encode($array);
                }else if(strcasecmp($postContent->field, 'type') ==0){
                        $noti->setProperty('type', $postContent->value);
                        $noti->save();
                        $array = $noti->getProperties();
                        $array['meetingID']=$noti->getId();
                        echo json_encode($array);
                }else if(strcasecmp($postContent->field, 'nodeID') ==0){
                        $noti->setProperty('nodeID', $postContent->value);
                        $noti->save();
                        $array = $noti->getProperties();
                        $array['notiID']=$noti->getId();
                        echo json_encode($array);
                }else if(strcasecmp($postContent->field, 'description') ==0){
                        $noti->setProperty('description', $postContent->value);
                        $noti->save();
                        $array = $noti->getProperties();
                        $array['notiID']=$noti->getId();
                        echo json_encode($array);
                }
                        $array = $noti->getProperties();
                        $array['notiID']=$noti->getId();
                        echo json_encode($array);
        }
}else if(strcasecmp($_SERVER['REQUEST_METHOD'], 'DELETE') == 0){      
	//delete notification DELETE
        //get the id
        preg_match("#(\d+)#", $_SERVER['REQUEST_URI'], $id);
        
        //get the node
        $node = $client->getNode($id[0]);
        //make sure the node exists
        if($node != NULL){
                //check if node has notification index
                $noti = $notiIndex->findOne('ID', ''.$id[0]);
                                
                //only delete the node if it's a notification
                if($noti != NULL){
                        //get the relationships
                        $relations = $noti->getRelationships();
                        foreach($relations as $rel){
                                //remove all relationships
                                $rel->delete();
                        }                
                        
                        //delete node and return true
                        $noti->delete();
                       $array = array('valid'=>'true');
 			echo json_encode($array);
                } else {
                        //return an error otherwise
                        $errorarray = array('errorID' => '4', 'errorMessage'=>'Given node ID is not a notification node');
			 //return an error otherwise
			echo '"errorID":"4", "errorMessage":"Given node ID is not a notification node"}';
 		}
		echo json_encode($errorarray);
	} else {
      //return an error if ID doesn't point to a node
		echo '{"errorID":"5", "errorMessage":"Given node ID is not recognized in database"}';
		$errorarray = array('errorID' => '5', 'errorMessage'=>'Given node ID is not recognized in database');
		echo json_encode($errorarray);
	}
}else{
        echo $_SERVER['REQUEST_METHOD'] ." request method not found in Notification";
}
?>
