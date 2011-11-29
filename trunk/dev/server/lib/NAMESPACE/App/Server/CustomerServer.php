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
class CustomerServer extends NAMESPACE_App_Server
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
	 * URL地址：/customer/customerList
	 * 提交方式：GET
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /customer/customerList
	 * @method get
	 */
	public function customerListAction ()
	{
		$this->doAuth();
		
		$customerDao = $this->dao->load('Core_Customer');
		$customerList = $customerDao->getListByPage();
		$this->render('10000', 'Get customer list ok', array(
			'Customer.list' => $customerList
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：测试登录接口
	 * <code>
	 * URL地址：/customer/customerCreate
	 * 提交方式：POST
	 * 参数#1：name，类型：STRING，必须：YES
	 * 参数#1：pass，类型：STRING，必须：YES
	 * 参数#1：sign，类型：STRING，必须：YES
	 * 参数#1：face，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /customer/customerCreate
	 * @params name '' STRING
	 * @params pass '' STRING
	 * @params sign '' STRING
	 * @params face '' STRING
	 * @method post
	 */
	public function customerCreateAction ()
	{
		$this->doAuth();
		
		$name = $this->param('name');
		$pass = $this->param('pass');
		$sign = $this->param('sign');
		$face = $this->param('face');
		if ($name && $pass && $sign && $face) {
			$customerDao = $this->dao->load('Core_Customer');
			$customerDao->create(array(
				'name'	=> $name,
				'pass'	=> $pass,
				'sign'	=> $sign,
				'face'	=> $face
			));
			$this->render('10000', 'Create customer ok');
		}
		$this->render('10004', 'Create customer failed');
	}
}