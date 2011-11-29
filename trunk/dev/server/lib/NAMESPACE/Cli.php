<?php
/**
 * NAMESPACE Cli
 *
 * @category   NAMESPACE
 * @package    NAMESPACE_Cli
 * @author     James.Huang <huangjuanshi@snda.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */
 
require_once 'Hush/Cli.php';

/**
 * @package NAMESPACE_Cli
 */
class NAMESPACE_Cli extends Hush_Cli
{	
	/**
	 * Implement Hush_Cli run method
	 * Used by bin/cli.php
	 * @return void
	 */
	public function run ()
	{
		$classFile = 'NAMESPACE/Cli/' . ucfirst(strtolower($this->_className)) . '.php';
		$className = 'NAMESPACE_Cli_' . ucfirst(strtolower($this->_className));
		require_once $classFile;
		$cli = new $className();
		$cli->start();
	}

	/**
	 * Print header
	 */
	protected function _printHeader ()
	{
		// print command list
		echo "\n----------------------------------------------------------\n";
		echo "Cli Class : " . get_class($this) . "\n";
		echo "----------------------------------------------------------\n\n";
	}
	
	/**
	 * Get config file
	 * @param string $dbName
	 * @param string $dbType
	 * @return string
	 */
	protected function _getCmdParams ($dbName = '', $dbType = 'mysql')
	{
		switch ($dbType) {
			case 'mysql':
				return ' -h' . Hush_Db_Config::DEFAULT_HOST
					 . ' -P' . Hush_Db_Config::DEFAULT_PORT
					 . ' -u' . Hush_Db_Config::DEFAULT_USER
					 . ' -p' . Hush_Db_Config::DEFAULT_PASS
					 . ' ' . $dbName;
			default:
				return '';
		}
	}
	
	/**
	 * Get backup sql file
	 * @param string $dbName
	 * @param string $dbType
	 * @return string
	 */
	protected function _getSqlBackup ($dbName, $dbType = 'mysql')
	{
		switch ($dbType) {
			case 'mysql':
				return __DAT_DIR . '/dbsql/mysql.' . $dbName . '.' . date('Y-m-d') . '.sql';
			default:
				return '';
		}
	}
}
