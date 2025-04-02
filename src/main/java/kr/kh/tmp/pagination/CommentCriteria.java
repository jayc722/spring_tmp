package kr.kh.tmp.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCriteria extends Criteria{
	private int po_num;
	
	public CommentCriteria(int num, int perPageNum) {
		super(num, perPageNum);
	}

	@Override
	public String toString() {
		return "PostCriteria [po_num=" + po_num + ", " + super.toString() + "]";
	}
	

	
}
