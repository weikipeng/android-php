<?php 

$_DataMap = array(
	'Blog'	=> array(
		'id' => 'id',
		'author' => 'author',
		'content' => 'content',
		'comment' => 'comment',
		'uptime' => 'uptime',
	),
	'Customer' => array(
		'id' => 'id',
		'sid' => 'sid',
		'name' => 'name',
		'sign' => 'sign',
		'face' => 'face',
		'blogcount' => 'blogcount',
		'fanscount' => 'fanscount',
		'uptime' => 'uptime',
	)
);

function M ($model, $data)
{
	global $_DataMap;
	$dataMap = isset($_DataMap[$model]) ? $_DataMap[$model] : null;
	if ($dataMap) {
		$dataRes = array();
		foreach ((array) $data as $k => $v) {
			if (array_key_exists($k, $dataMap)) {
				$mapKey = $dataMap[$k];
				$dataRes[$mapKey] = $v;
			}
		}
		return $dataRes;
	}
	return $data;
}