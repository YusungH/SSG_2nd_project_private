<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<script src="webjars/jquery/3.7.1/jquery.min.js"></script>
<script>
	const categoriesKr = {
		'DA' : '달걀/유제품',
		'FR' : '과일',
		'FS' : '생선/해산물',
		'FZ' : '냉동식품',
		'MT' : '육류',
		'SC' : '소스/조미료',
		'SI' : '반찬',
		'SN' : '과자',
		'VG' : '채소/신선식품'
	};
	const categoriesEng = {
		'DA' : 'Diary',
		'FR' : 'Fruit',
		'FS' : 'Fish',
		'FZ' : 'Frozen',
		'MT' : 'Meat',
		'SC' : 'Sauce',
		'SI' : 'Side dish',
		'SN' : 'Snack',
		'VG' : 'Vegetable'
	};

	$(document).ready( function() {
		
		// 전체 선택과 해제
		$("#checkAll").on("click", function(){
			var chk = this.checked;
			$(".check").each((idx, ele) => {
				ele.checked = chk;
			});
		});

		// 수량 변경 버튼 클릭 이벤트 처리
		$(".btn-success").on("click", function() {
			event.preventDefault();
			var row = $(this).closest("tr");
			var num = row.find("input#hidden_num").val();
			var amount = row.find("input#amount").val();
			var queryString = "num=" + num + "&amount=" + amount;
			var url = "updateRefrigeratorStock?" + queryString;
			location.href = url;
		});
		
		
		// 전체 변경 사항 저장하기
		$("#saveAll").on("click", function(){
			event.preventDefault();
			var f = $("form")[0];
			f.action = "#";
			f.method = "get";
			f.submit();
		});
		
		
		// 선택한 상품 삭제하기
		$("#deleteAll").on("click", function(){
			var f = $("form")[0];
			f.action = "#";
			f.method = "get";
			f.submit();
		});
		
		
		// 하단 냉장고 상품 추가 선택 시
		$("#add_gCode").on("change", function() {
			var imageName = $("#add_gCode").val();
			$("#addItemImage").attr("src", "images/items/" + imageName + ".png");

			// 상품 이름 입력
			var itemName = $(this).find('option:selected').text();
			$("#add_gName").val(itemName);

			// 상품코드 칸에 입력
			$("#viewCode").val(imageName);

			// 카테고리 칸에 입력
			var codePrefix = imageName.substring(0, 2); // 상품 코드 앞 2자리 추출
			var categoryKrName = categoriesKr[codePrefix]; // 보여주는 카테고리 이름
			$("#viewCategory").val(categoryKrName);

			var categoryEngName = categoriesEng[codePrefix]; // 저장할 카테고리 이름
			$("#viewCategory2").val(categoryEngName);

		});

		// 내 냉장고에 상품 추가하기
		$("#addRef").on("click", function() {
			var f = $("form")[1];
			f.action = "refrigeratorAdd";
			f.method = "get";
			f.submit();
		});

	});//end ready
	
</script>

<form>
	<div class="TodoApp">
		<div class="container">
			<div>
				<table class="table align-middle text-center">
					<thead>
						<tr>
							<th><input type="checkbox" name="item_chk" id="checkAll"> 전체선택</th>
							<th>상품명</th>
							<th>재고 수량</th>
							<th>수량 변경</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${ refrigeratorList }">
							<tr>
								<td><input type="checkbox" name="check" class="check"></td>
								<td><input type="hidden" name="hidden_num" id="hidden_num" value="${ item.num }"> 
									<img src="images/items/${ item.gCode }.png" width="160" height="160" id="gCode"><br>${ item.gName }
								</td>
								<td><input type="number" name="stock" id="amount" value="${ item.rStock }" style="text-align: right;" size="3" min="0" max="99">
									<br><br>
								</td>
								<td>
									<button class="btn btn-outline-success">수량 변경</button>
								</td>
								<td><a href="refrigeratorDelete?num=${ item.num }" class="btn btn-outline-danger">삭제</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="container">
					<button class="btn btn-success" id="saveAll">변경사항 저장</button>
					&nbsp;&nbsp;&nbsp;
					<button class="btn btn-danger" id="deleteAll">선택 상품 삭제</button>
				</div>
			</div>
		</div>
	</div>
</form>
<div style="height: 100px;">

</div>
<form>
	<div class="container">
		<div class="TodoApp">
			<p class="text-center">내 냉장고에 추가할 상품을 선택하세요.</p>
			<table class="table align-middle text-center">
				<thead>
					<tr>
						<th>상품코드</th>
						<th>카테고리</th>
						<th>상품 이미지</th>
						<th>상품명</th>
						<th>수량(1~99)</th>
						<th>저장</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" name="gCode" id="viewCode" value="" size="7"></td>
						<td>
							<input type="text" name="viewCategory" id="viewCategory" value="" size="12">
							<input type="hidden" name="gCategory" id="viewCategory2" value="">
						</td>
						<td><img src="images/items/white.png" width="100" height="100" id="addItemImage" alt="상품 이미지"></td>
						<td>
							<select name="itemName" id="add_gCode">
								<option>추가할 상품을 선택하세요.</option>
								<c:forEach var="goods" items="${ refGoodsList }">
									<option value=${ goods.gCode }>${ goods.gName }</option>
								</c:forEach>
							</select>
							<input type="hidden" value="" name="gName" id="add_gName">
						</td>
						<td><input type="number" min="1" max="99" name="rStock" id="add_rStock" value="1" size="2"></td>
						<td><button type="button" class="btn btn-outline-success" id="addRef">저장</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</form>