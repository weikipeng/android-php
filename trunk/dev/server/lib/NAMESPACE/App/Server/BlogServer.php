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
class BlogServer extends NAMESPACE_App_Server
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
	 * URL地址：/blog/blogList
	 * 提交方式：GET
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /blog/blogList
	 * @method get
	 */
	public function blogListAction ()
	{
		$this->doAuth();
		
		$blogDao = $this->dao->load('Core_Blog');
		$blogList = $blogDao->getListByCustomer($this->customer['id']);
		$this->render('10000', 'Get blog list ok', array(
			'Blog.list' => $blogList
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：测试首页接口
	 * <code>
	 * URL地址：/blog/blogView
	 * 参数#1：blogId，类型：STRING，必须：YES，示例：1
	 * 提交方式：POST
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /blog/blogView
	 * @params blogId 1 STRING
	 * @method post
	 */
	public function blogViewAction ()
	{
		$this->doAuth();
		
		$blogId = intval($this->param('blogId'));
		
		$blogDao = $this->dao->load('Core_Blog');
		$blogItem = $blogDao->read($blogId);
		
		$customerDao = $this->dao->load('Core_Customer');
		$customerItem = $customerDao->read($blogItem['customerid']);
		
		$this->render('10000', 'Get blog ok', array(
			'Customer' => $customerItem,
			'Blog' => $blogItem
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：测试登录接口
	 * <code>
	 * URL地址：/blog/blogCreate
	 * 提交方式：POST
	 * 参数#1：content，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /blog/blogCreate
	 * @params content '' STRING
	 * @method post
	 */
	public function blogCreateAction ()
	{
		$this->doAuth();
		
		$content = $this->param('content');
		if ($content) {
			$blogDao = $this->dao->load('Core_Blog');
			$blogDao->create(array(
				'customerid'	=> $this->customer['id'],
				'desc'			=> '',
				'title'			=> '',
				'content'		=> $content,
				'commentcount'	=> 0
			));
			// add customer blogcount
			$customerDao = $this->dao->load('Core_Customer');
			$customerDao->addBlogcount($this->customer['id']);
			$this->render('10000', 'Create blog ok');
		}
		$this->render('10005', 'Create blog failed');
	}
}