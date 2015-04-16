<?php
	//DB COnnection Details
	$server = 'localhost';
	$user = 'dezsys';
	$passwd = 'letmein';



	//Members
	$failure = '';
	$message = '';
	$entryTable = '';
	
	//Verbindungsaufbau
	$link= mysql_connect($server, $user, $passwd);
		
	//Verbindungsprüfung
	if (!$link) {
		$failure.= 'Connection ' . $server . ' failed: ' . mysql_error();
	} else {
		//Prüfen ob Datenbank vorhanden
		$db= mysql_select_db('iknow');

		if (!$db) {
			$failure .= 'Database not found';
		} else {

			//------------------------------------------------------------------------------------------------
			switch ($_GET['submit']) {
				case 'Save':

					if($_GET['topic'] == '' || $_GET['text'] == '') {
						$failure = 'Please fill both fields befor saving!';
					} else {
						if (strlen($_GET['topic']) > 10 || strlen($_GET['text']) > 100) {
							$failure = 'Please make sure you have not used more than 10 chars in topic and 100 in text!';
						} else {
							mysql_query("INSERT INTO KnowledgeBase VALUES('', '" . $_GET['text'] . "', '" . $_GET['topic'] . "');");
							if (!mysql_error()) {
								$message = 'Successfully created' . $_GET['topic'];
							} else {
								$failure = 'Could not create entry!';
							}
						}
					}
					break;

				case 'REMOVE':
					$result = mysql_query('DELETE FROM KnowledgeBase WHERE ID = ' . $_GET['id'] . ';');
					if (!mysql_error()) {
								$message = 'Successfully removed!';
							} else {
								$failure = 'Could not remove entry!';
							}
					break;
				
				default:
					$result = mysql_query('SELECT * FROM KnowledgeBase;', $link);
					if(mysql_error() != '') {
						$failure = 'Reading error!' . mysql_error();
					} else {
						$message = 'Found ' . mysql_num_rows($result) . ' entries';
						//Creating HTML out of entries
						$entryTable = '<table border="0" cellpadding="10" width="100%"><tr align="left"><th>X</th><th>Topic</th><th>Content</th></tr>';
						while ($row = mysql_fetch_array($result)) {
							if($row['ID']%2 == 0) {
								$entryTable .= '<tr bgcolor="#61B8C4">';
							} else {
								$entryTable .= '<tr bgcolor="#ffffff">';
							}
							$entryTable .= '<td><input type="checkbox" name="id" id="id" value="' . $row['ID'] . '"></td><td>' . $row ['topic'] . '</td><td>' . $row ['text'] . '</td></tr>';
						}
						$entryTable .= '</table>';
					}
			}

			//------------------------------------------------------------------------------------------------
		}
	}
	mysql_close($link);
	include 'index-form.php';
?>