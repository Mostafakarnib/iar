/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Sammy Guergachi <sguergachi at gmail.com>
 * Created: Oct 29, 2017
 */

/* Pirlo User with password: 123456 */
INSERT INTO `pircs`.`tbl_operator` (`col_id`,`col_insertion_date`, `col_password`, `col_recieving_emergency_emails`, `col_role`,
 `col_title`, `col_operator_type`, `col_username`, `HOSPITAL_col_id`) 
VALUES (1,'2017-11-01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', false, 'ROOT', 'Pirlo', null, 'pirlo', null);

/* QA User with password: 123456 */
INSERT INTO `pircs`.`tbl_operator` (`col_id`,`col_insertion_date`, `col_password`, `col_recieving_emergency_emails`, `col_role`,
 `col_title`, `col_operator_type`, `col_username`, `HOSPITAL_col_id`) 
VALUES (2,'2017-11-01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', true, 'ADMIN', 'Quality Manager', 'QUALITY_MANAGER', 'qa', null);

/* Regular User with password: 123456 */
INSERT INTO `pircs`.`tbl_operator` (`col_id`,`col_insertion_date`, `col_password`, `col_recieving_emergency_emails`, `col_role`,
 `col_title`, `col_operator_type`, `col_username`, `HOSPITAL_col_id`) 
VALUES (3,'2017-11-01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', false, 'USER', '', 'REGULAR_USER', 'user', null);
