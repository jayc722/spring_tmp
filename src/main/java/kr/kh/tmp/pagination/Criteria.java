package kr.kh.tmp.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Criteria {
	
	protected int page = 1;//현재 페이지 : 기본값 - 1
	protected int perPageNum = 10;//한 페이지에서 컨텐츠 개수 : 기본값 - 10
	
	protected String search = ""; //검색어 : 기본값 - 빈문자열=> 전체 검색
	protected String type; //검색 타입 
	
	public Criteria(int page, int perPageNum) {
		this.page = page;
		this.perPageNum = perPageNum;
	}
	public int getPageStart() {					//pageStart 호출하면 mapper에서 얘를 가져오기 때문에 pageStart를 변수처럼 사용 가능
		return (page - 1) * perPageNum;
	}
}