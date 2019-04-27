<?php

error_reporting(E_ERROR | E_PARSE);
header('Content-type: application/json; charset=utf-8');
header('Expires: Sun, 01 Jan 2014 00:00:00 GMT');
header('Cache-Control: no-store, no-cache, must-revalidate');
header('Cache-Control: post-check=0, pre-check=0', FALSE);
header('Pragma: no-cache');

$response = array();
$request = explode('/', trim($_SERVER['PATH_INFO'], '/'));
$action = strtolower(array_shift($request));
$id = array_shift($request);

$db = new mysqli('localhost', 'root', '', 'my_db');
if ($db->connect_errno) {
    $response['error_code'] = 1;
    $response['error_message'] = 'การเชื่อมต่อฐานข้อมูลล้มเหลว';
    echo json_encode($response);
    exit();
}
$db->set_charset("utf8");

switch ($action) {
    case 'login':
        doLogin();
        break;

    default:
        $response['error_code'] = 2;
        $response['error_message'] = 'No action specified or invalid action.';
        break;
}

$db->close();
echo json_encode($response);
exit();

function doLogin() {
    global $db, $response;

    $username = $_GET['username'];
    $password = $_GET['password'];

    // SELECT * FROM user WHERE username = 'test' AND password = '1234'

    $sql = "SELECT * FROM user WHERE username = '$username' AND password = '$password' ";
    if ($result = $db->query($sql)) {
        $response['error_code'] = 0;
        $response['error_message'] = '';

        if ($result->num_rows == 0) {
            $loginStatus = FALSE;
        } else {
            $loginStatus = TRUE;
        }

        $response['login_status'] = $loginStatus;

        $result->close();
    } else {
        $response['error_code'] = 3;
        $response['error_message'] = 'เกิดข้อผิดพลาดในการเชื่อมต่อฐานข้อมูล' . $db->error;
    }
}

?>