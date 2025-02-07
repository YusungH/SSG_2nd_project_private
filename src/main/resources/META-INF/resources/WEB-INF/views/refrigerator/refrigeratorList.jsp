<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<script src="webjars/jquery/3.7.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
        $("#stockUp").on("click", function(){
            var stock = Number.parseInt($("#stock").val());
            stock++;
            $("#stock").val(stock);
        });

        $("#stockDown").on("click", function(){
            var stock = Number.parseInt($("#stock").val());
            stock--;
            if (stock < 0) stock = 0;
            $("#stock").val(stock);
        });
        
        // 상품 이미지 변경
        $("#name").on("change", function() {
            var imageName = $("#name").val();
            $("#addItemImage").attr("src", "images//" + imageName + ".png");
        });
        
        // 변경 사항 저장
        $("save").on("submit", function(){
        	
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
                                <th>번호</th>
                                <th>상품명</th>
                                <th>재고 수량</th>
                                <th>삭제</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>
                                    <img src="images/DA-0001.png" width="160" height="160" id="image"><br>
                                    슬라이스 치즈
                                </td>
                                <td>
                                    <input type="text" name="stock" id="stock" value="20" style="text-align:right;"
                                        size="3">
                                    <br><br>
                                    <button type="button" id="stockUp" class="btn btn-light">+</button>
                                    <button type="button" id="stockDown" class="btn btn-light">-</button>
                                </td>
                                <td>
                                    <button type="button" class="btn btn-outline-warning">삭제</button>
                                </td>
                            </tr>
                        </tbody>

                    </table>
                </div>
                <div class="btn btn-success m-5" id="save">저장</div>
            </div>
        </div>
    </form>

    <form>
        <div class="container">
            <div class="TodoApp">
                <p class="text-center">추가할 상품과 수량을 선택하세요.</p>
                <table class="table align-middle text-center">
                    <thead>
                        <tr>
                            <th>상품 이미지</th>
                            <th>상품명</th>
                            <th>수량(1~99)</th>
                            <th>저장</th>
                            <th>초기화</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <img src="images/none.png" witdh="100" height="100" id="addItemImage" alt="상품 이미지">
                            </td>
                            <td>
                                <select name="itemName" id="gCode" size="1" >
                                <option value="none">추가할 상품을 선택하세요</option>
                                <option value="DA-0001">슬라이스 치즈</option>
                                <option value="DA-0002">달걀</option>
                                <option value="FR-0001">귤</option>
                                <option value="FR-0002">토마토</option>
                                <option value="FS-0001">고등어</option>
                                <option value="FS-0002">낙지</option>
                                <option value="FZ-0001">군만두</option>
                                <option value="FZ-0002">감자튀김</option>
                                </select>
                            </td>
                            <td>
                                <input type="number" min="1" max="99" id="rStock">
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-success" id="add">추가하기</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" id="cancel">초기화</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
<div>
<h3> 임시 데이터입니다. </h3>
</div>