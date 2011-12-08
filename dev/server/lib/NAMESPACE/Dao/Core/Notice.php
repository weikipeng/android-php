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
require_once 'NAMESPACE/Util/Image.php';

/**
 * @package NAMESPACE_Dao_Core
 */
class Core_Notice extends NAMESPACE_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'notice';
	
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
	 * Get notification count
	 * @param int $customerId
	 * @return array
	 */
	public function getCountByCustomer ($customerId)
	{
		$sql = $this->dbr()->select()->from($this->t1, '(1)')
			->where("customerid = ?", $customerId)
			->where("status = 0");
		
		return $this->dbr()->fetchOne($sql);
	}
	
	/**
	 * Get notification list
	 * @param int $customerId
	 * @return array
	 */
	public function getListByCustomer ($customerId)
	{
		$sql = $this->dbr()->select()->from($this->t1, '*')
			->where("customerid = ?", $customerId)
			->where("status = 0");
		
		return $this->dbr()->fetchAll($sql);
	}
}
