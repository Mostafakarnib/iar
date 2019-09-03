

UPDATE `tbl_root_cause_analysis` SET `col_request_details`= `tbl_root_cause_analysis`.`col_details`;
ALTER TABLE `tbl_root_cause_analysis` DROP COLUMN `col_details`;