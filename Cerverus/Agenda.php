<?php
/**
 * Include the API PHP file for neo4j
 */
namespace Everyman\Neo4j;
require("phar://neo4jphp.phar");


/**
 *	Create a graphDb connection 
 */
$client= new Client();

	//get the index
	$agendaIndex = new Index\NodeIndex($client, 'agendas');
	$agendaIndex->save();
if(strcasecmp($_SERVER['REQUEST_METHOD'], 'POST')==0){
	//createAgenda method
	
	//get the json string post content
	$postContent = json_decode(@file_get_contents('php://input'));
	
	//create the node
	$agendaNode= $client->makeNode();
	
	//sets the property on the node
	$agendaNode->setProperty('title', $postContent->title)
		->setProperty('meeting', $postContent->meeting);
	//actually add the node in the db
	$agendaNode->save();
	
	foreach($postContent->content as $topic){
		//pass the topic to Topic.php's create Topic method
		$request = new HttpRequest('http://csse371-04.csse.rose-hulman.edu/Topic/', HttpRequest:METH_POST);
		$request->addPostFields(array('title' => $topic->title, 'time' => $topic->time, 'suptopic' => $topic->subtopic));
		$result = $request->send();
		$topicNode = $client->getNode($result);
		//make a relation to the topic 'HAS_TOPIC'
		$topicRel = $agendaNode->relateTo($topicNode, 'HAS_TOPIC')
			->save();
	}
	
	//relate agenda to meeting
	$meeting = $client->getNode($postContent->meeting);
    $meetingRel = $meeting->relateTo($agenda, 'FOLLOWS')
                ->save();
                
	//get properties on the node
	$agendaProps= $agendaNode->getProperties();
	
	$response= $agendaIndex->add($agendaNode, 'user', $agendaProps['user']);
	echo $response;
}else if(strcasecmp($_SERVER['REQUEST_METHOD'], 'GET')==0){
	//getAgendaInfo
	$agendaNode=$client->getNode($_GET['id']);
	foreach ($agendaNode->getProperties() as $key => $value) {
		echo "$key: $value\n";
	}
}else if(strcasecmp($_SERVER['REQUEST_METHOD'], 'PUT')==0){
	//updateAgenda
	$postContent = json_decode(@file_get_contents('php://input'));
	
	$agenda=$client->getNode($postContent->agendaID);

	if(sizeof($note) >0){
		if(strcasecmp($postContent->field, 'title') ==0){
			$agenda->setProperty('title', $postContent->value);
			$agenda->save();
			$array = $agenda->getProperties();
			echo json_encode($array);
		}else if(strcasecmp($postContent->field, 'meeting') ==0){
			$agenda->setProperty('meeting', $postContent->value);
			$meeting = $client->getNode($postContent->value);
			$relations = $agenda->getRelationships(array('FOLLOWS'));
			$relations[0]->setEndNode($meeting)
				->save();
			$agenda->save();
			$array = $agenda->getProperties();
			echo json_encode($array);
		}else if(strcasecmp($postContent->field, 'content') ==0){
			//delete all the topics and replace them with the new topics in content. TODO
			$agenda->setProperty('content', $postContent->value);
			$agenda->save();
			$array = $agenda->getProperties();
			echo json_encode($array);
		}else{
			echo "No node updated.";
		}
	
	}else{
		echo "FALSE node not found";
	}
}else if(strcasecmp($_SERVER['REQUEST_METHOD'], 'DELETE')==0){
	//deleteAgenda
	//get the id
        preg_match("#(\d+)#", $_SERVER['REQUEST_URI'], $id);
        
        //get the node
        $node = $client->getNode($id[0]);
        //make sure the node exists
        if($node != NULL){
                //check if node has agenda index
                $agenda = $agendaIndex->findOne('ID', ''.$id[0]);
                                
                //only delete the node if it's an agenda
                if($agenda != NULL){
                		//get the relationships which are 'HAS_TOPIC'
                		$relations2 = $agenda->getRelationships(array('HAS_TOPIC'));
                		foreach($relations2 as $rel){
                			//remove the relation and delete the topic it's associated with
                			//delete Topic
                			$request = new HttpRequest('http://csse371-04.csse.rose-hulman.edu/Topic/', HttpRequest:METH_DELETE);
                			$result = $request->send();
                			$rel->delete();
                		}
                        //get the relationships
                        $relations = $agenda->getRelationships();
                        foreach($relations as $rel){
                                //remove all relationships
                                $rel->delete();
                        }                
                        
                        //delete node and return true
                        $agenda->delete();
                  	    $array = array('valid'=>'true');
 						echo json_encode($array);
                } else {
                        //return an error otherwise
                        $errorarray = array('errorID' => '4', 'errorMessage'=>'Given node ID is not an agenda node');
 				}
		echo json_encode($errorarray);
		} else {
     	//return an error if ID doesn't point to a node
		echo '{"errorID":"5", "errorMessage":"Given node ID is not recognized in database"}';
		$errorarray = array('errorID' => '5', 'errorMessage'=>'Given node ID is not recognized in database');
		echo json_encode($errorarray);
	}
}else{
	echo $_SERVER['REQUEST_METHOD'] ." request method not found";
}
?>