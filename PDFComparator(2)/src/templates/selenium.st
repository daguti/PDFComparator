<html>
<head><style type='text/css'>
body, table {
    font-family: Verdana, Arial, sans-serif;
    font-size: 12;
}
table {
    border-collapse: collapse;
    border: 1px solid #ccc;
}
th, td {
    padding-left: 0.3em;
    padding-right: 0.3em;
}
a {
    text-decoration: none;
}
.title {
    font-style: italic;
}
.selected {
    background-color: #ffffcc;
}
.status_done {
    background-color: #eeffee;
}
.status_passed {
    background-color: #ccffcc;
}
.status_failed {
    background-color: #ffcccc;
}
.status_error {
    background-color: #ff9999;
}
.breakpoint {
    background-color: #cccccc;
    border: 1px solid black;
}
</style>
<title>Test suite results</title></head>
<body>
	<h1>Test results</h1>
	<table>
<tr class="status_$ok$">
<td>result:</td>
<td>$filesOk$</td>
</tr>
<tr>
<td>totalTime(s):</td>
<td>$time$</td>
</tr>
<tr>
<td>numTestTotal:</td>
<td>$nTest$</td>
</tr>
<tr>
<td>numTestPasses:</td>
<td>$nOk$</td>
</tr>
<tr>
<td>numTestFailures:</td>
<td>$nTestFails$</td>
</tr>
<tr>
<td>numCommandPasses:</td>
<td>$nOk$</td>
</tr>
<tr>
<td>numCommandFailures:</td>
<td>$nFails$</td>
</tr>
<tr>
<td>numCommandErrors:</td>
<td>$nErr$</td>
</tr>
</tr>
		<tr>
			<td>
				<table id="suiteTable" class="selenium" border="1" cellpadding="1" cellspacing="1">
					<tbody>
						<tr class="title    status_$ok$"><td><b>$title$</b></td></tr>
						<tr class="  status_$ok$"><td><a href="#files">Files</a></td></tr>
					</tbody>
				</table>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	</br>
	</br>
	<table>
		$if(files)$
			<tr>
				<td><a name="files">Files</a><br/>
					<table border="1" cellpadding="1" cellspacing="1">
						<thead>
							<tr class="title status_$filesOk$"><td>Current</td><td>Base</td><td>Reason</td>$if(fileError)$<td>Error</td>$endif$</tr>
						</thead>
						<tbody>
							$files:{file|
								<tr class="  status_$file.ok$" style="cursor: pointer;">
										<td><a href="$file.url$">$file.name$</a></td>
										<td><a href="$file.baseUrl$">$file.baseName$</a></td>
										<td>$file.reason$</td>
								</tr>
							}$
						</tbody>
					</table>
				</td>
			</tr>
		$endif$
	</table>
	<pre></pre>
</body></html>
