<?php
	//DB COnnection Details
	$server = 'localhost';
	$user = 'dezsys';
	$passwd = 'letmein';

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
					$entryTable .= '<td><input type="checkbox" name="id" value="' . $row['ID'] . '"></td><td>' . $row ['topic'] . '</td><td>' . $row ['text'] . '</td></tr>';
				}
				$entryTable .= '</table>';
			}
		}
	}
?>
<html>
	<head>
		<title>
			SOAP | iknow
		</title>
		<link href="style.css" type="text/css" rel="stylesheet">
		<script src="https://code.jquery.com/jquery-1.6.2.min.js"></script>
		<script src="client.js"></script>
	</head>
	<body>
		<div id="headline">
			<div id="logo">
				iknow |
			</div>
			<div id="notifies">
				<font color="red"><?php if(isset($failure)) echo $failure;?></font>
				<font color="green"><?php if(isset($message)) echo $message;?></font>
			</div>
		</div><!-- headline -->
		<div id="wrapper">
			<div id="nav">
				<form name="navigation" method="GET" action="index.php">
					<input type="button" onclick="flushEntry();openEntry();" value="ADD">
					<input type="submit" name="action" value="REMOVE">
					<input type="button" onclick="updateEntry();openEntry();" name="action" value="UPDATE SELECTED">
				</form>
			</div>
			<div id="table">
				<?php if(isset($entryTable)) echo $entryTable;?>
			</div>
		</div>
		<!------------------------------------------------------------------------------------------------------ Entry View -->
		<div id="light">
			<div id="e-header">
				Entry View
			</div>
			<div id="e-content">
				<!-- Content-Wrapper -->
				<form method="GET" action="index.php">
					Topic:<br>
					<input type="text" name="topic" id="topic" placeholder="Topic of the entry..." alt="Title of the event"><br>
					<br>
					Text:<br>
					<textarea name="text" id="text" placeholder="Something you'd like to say..." cols="30" rows="3"></textarea><br>
					<br>
					<input type="submit" name="submit" value="Save">
	           		<input type="button" onCLick="closeEntry();" name="Cancel" value="Cancel">
           		</form>
			</div>
		</div>
		<!------------------------------------------------------------------------------------------------------ Entry View -->
		<div id="fade" onClick="closeEntry();"></div>
	</body>
</html>