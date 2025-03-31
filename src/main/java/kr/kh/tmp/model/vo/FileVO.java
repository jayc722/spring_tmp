package kr.kh.tmp.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor// 생성자 만들었기때문에 기본생성자 추가해야됨
public class FileVO {
	private int fi_num;
	private String fi_ori_name;
	private String fi_name;
	private int fi_po_num;
	
	
	public FileVO(String fi_ori_name, String fi_name, int fi_po_num) {	
		this.fi_ori_name = fi_ori_name;
		this.fi_name = fi_name;
		this.fi_po_num = fi_po_num;
	}

}
