<?php
/**
 * NAMESPACE App
 *
 * @category   NAMESPACE
 * @package    NAMESPACE_App
 * @author     James.Huang <huangjuanshi@snda.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'Hush/Service.php';

/**
 * @package NAMESPACE_App
 */
class NAMESPACE_App_Server extends Hush_Service
{
	/**
	 * @var array
	 */
	protected $_msgs = array();
	
	/**
	 * Initialize mongo dao
	 * 
	 * @return array
	 */
	public function __init ()
	{
		parent::__init();
		
		// init dao
		require_once 'NAMESPACE/Dao.php';
		$this->dao = new NAMESPACE_Dao();
		
		// init url
		require_once 'NAMESPACE/Util/Url.php';
		$this->url = new NAMESPACE_Util_Url();
		
		// init session
		require_once 'NAMESPACE/Util/Session.php';
		$this->session = new NAMESPACE_Util_Session();
	}
	
	/**
	 * Logging mongo dao
	 * 
	 * @return array
	 */
	public function __done ()
	{
		parent::__done();
	}
	
	/**
	 * Get all messages array
	 * 
	 * @return array
	 */
	public function getMsg ()
	{
		return $this->_msgs;
	}
	
	/**
	 * Add error messages into template, and then render it
	 * 
	 * @param string $msg Error message
	 * @return NAMESPACE_App_Service
	 */
	public function addMsg ($msg)
	{
		// get msg from ini file
		static $msg_tpl_arr;
		if (!$msg_tpl_arr) {
			if (!file_exists(__MSG_INI_FILE)) {
				require_once 'NAMESPACE/App/Exception.php';
				throw new NAMESPACE_App_Exception('Message ini file can not be found');
			}
			$msg_tpl_arr = parse_ini_file(__MSG_INI_FILE);
		}
		$msg_tpl_str = isset($msg_tpl_arr[$msg]) ? $error_tpl_arr[$msg] : 'undefined';
		
		// should do replace logic
		$args = func_get_args();
		@array_shift($args);
		if (sizeof($args) > 0) {
			$replace_old = array();
			$replace_new = array();
			foreach ($args as $id => $arg) {
				$replace_old[] = '{' . $id . '}';
				$replace_new[] = $arg;
			}
			$msg_tpl_str = str_replace($replace_old, $replace_new, $msg_tpl_str);
		}
		
		// set message into vars
		$this->_msgs[] = $msg_tpl_str;
		return $this;
	}
	
	/**
	 * Forward page by header redirection
	 * J2EE like method's name :)
	 * 
	 * @param string $url
	 * @return void
	 */
	public function forward ($url)
	{
		// append sid for url
		Hush_Util::headerRedirect($this->url->format($url));
		exit;
	}
	
	/**
	 * 
	 */
	public function render ($code, $message, $result = '')
	{
		// filter by datamap
		if (is_array($result)) {
			foreach ((array) $result as $model => $data) {
				$result[$model] = M($model, $data);
			}
		}
		// print json code
		echo json_encode(array(
			'code'		=> $code,
			'message'	=> $message,
			'result'	=> $result
		));
		exit;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// protected method
	
	/**
	 * @ingore
	 */
	public function doAuth ()
	{
		if (!isset($_SESSION['customer'])) {
			$this->render('10001', 'Please login firstly.');
		} else {
			$this->customer = $_SESSION['customer'];
		}
	}
	
	/**
	 * @ingore
	 */
	public function doAuthAdmin ()
	{
		if (!isset($_SESSION['admin'])) {
			$this->forward($this->apiAuth); // auth action
		} else {
			$this->admin = $_SESSION['admin'];
		}
	}
}
