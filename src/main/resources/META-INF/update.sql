/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sammy Guergachi <sguergachi at gmail.com>
 * Created: Dec 8, 2017
 */

UPDATE `tbl_root_cause_analysis` SET `col_request_details`= `tbl_root_cause_analysis`.`col_details`;
ALTER TABLE `tbl_root_cause_analysis` DROP COLUMN `col_details`;