<?php
/**
 * NAMESPACE App
 *
 * @category   NAMESPACE
 * @package    NAMESPACE_App_Server
 * @author     James.Huang <huangjuanshi@snda.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'NAMESPACE/App/Server.php';

/**
 * @package NAMESPACE_App_Server
 */
class NotifyServer extends NAMESPACE_App_Server
{
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 全局设置：
	 * <code>
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 */
	public function __init ()
	{
		parent::__init();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// service api methods
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：测试首页接口
	 * <code>
	 * URL地址：/notify/notice
	 * 提交方式：POST
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /notify/notice
	 * @method get
	 */
	public function noticeAction ()
	{
		$this->doAuth();
		
		// get extra customer info
		$nDao = $this->dao->load('Core_Notice');
		if ($nDao->getCountByCustomer($this->customer['id'])) {
			$nList = $nDao->getListByCustomer($this->customer['id']);
			$this->render('10000', 'Get notification ok', array(
				'Notice.list' => $nList
			));
		}
		$this->render('10012', 'Get notification failed');
	}
}