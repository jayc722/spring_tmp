package kr.kh.tmp.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCriteria extends Criteria{
	//이렇게 안하면 criteria에 직접 추가해야함(한군데서밖에 못씀)
	private int bo_num;
	
	public PostCriteria(int num, int perPageNum) {
		super(num, perPageNum);
	}

	@Override
	public String toString() {
		return "PostCriteria [bo_num=" + bo_num + ", " + super.toString() + "]";
	}
	

	
}
