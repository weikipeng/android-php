<?php
/**
 * NAMESPACE Dao
 *
 * @category   NAMESPACE
 * @package    NAMESPACE_Dao_Core
 * @author     James.Huang <shagoo@gmail.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'NAMESPACE/Dao/Core.php';
require_once 'NAMESPACE/Dao/Core/Customer.php';

/**
 * @package NAMESPACE_Dao_Core
 */
class Core_Blog extends NAMESPACE_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'blog';
	
	/**
	 * @static
	 */
	const TABLE_PRIM = 'id';
	
	/**
	 * Initialize
	 */
	public function __init () 
	{
		$this->t1 = self::TABLE_NAME;
		$this->k1 = self::TABLE_PRIM;
		
		$this->_bindTable($this->t1, $this->k1);
	}
	
	/**
	 * Get blog list 
	 * @param string $customerId Customer ID
	 */
	public function getListByCustomer ($customerId)
	{
		$list = array();
		$sql = $this->dbr()->select()
			->from($this->t1, "*")
			->where("{$this->t1}.customerid = ?", $customerId)
			->order("{$this->t1}.uptime desc");
		
		$res = $this->dbr()->fetchAll($sql);
		foreach ($res as $row) {
			$customerDao = new Core_Customer();
			$customer = $customerDao->read($row['customerid']);
			$blog = array(
				'id'		=> $row['id'],
				'content'	=> '<b>'.$customer['name'].'</b> : '.$row['content'],
				'comment'	=> '评论('.$row['commentcount'].')',
				'uptime'	=> $row['uptime'],
			);
			array_push($list, M('Blog', $blog));
		}
		return $list;
	}
}