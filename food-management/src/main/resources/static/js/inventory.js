$(document).ready(function() {
    // 編集ボタン押したとき
	$(".edit-btn").on("click", function () {
        const $row = $(this).closest("tr");
        const isEditing = $(this).find('i').hasClass('fa-xmark');
		$row.find('.edit-btn').addClass('d-none');
	    
	    if (isEditing) {
	        revertRowFromEditable($row);
	    } else {
	        convertRowToEditable($row);
	    }
		
    });
	
	//キャンセルボタン
	$('.cancel-btn').on('click', function () {
	    const $row = $(this).closest('tr');
		revertRowFromEditable($row);

	    // 保存方法
	    const storage = $row.find('.storageMethod').attr('data-original');
	    $row.find('.storageMethod').html(storage);

	    // 購入日
	    const purchaseDate = $row.find('.purchaseDate').attr('data-original');
	    $row.find('.purchaseDate').html(purchaseDate);

	    // 消費期限
	    const expirationDate = $row.find('.expirationDate').attr('data-original');
	    $row.find('.expirationDate').html(expirationDate);

	    // 数量 + 単位
	    const quantity = $row.find('.quantity').attr('data-original-quantity');
	    const unit = $row.find('.quantity').attr('data-original-unit');
	    $row.find('.quantity').html(`
	        <span>${quantity}</span>
	        <span class="form-text">${unit}</span>
	    `);

	    // ボタンの表示状態を戻す
		$row.find('.edit-btn').removeClass('d-none');
	    $row.find('.save-btn').addClass('d-none');
	    $row.find('.cancel-btn').addClass('d-none');
	});
	
    // 保存ボタン押したとき
    $('.save-btn').on('click', function() {
        var $row = $(this).closest('tr');
        var id = $row.data('id');		            

        var data = {
            storageMethod: $row.find('.storageMethod input').val(),
            purchaseDate: $row.find('.purchaseDate input').val(),
            expirationDate: $row.find('.expirationDate input').val(),
            quantity: $row.find('.quantity input').val()
        };

        // Ajaxで保存（ここはURLを適宜変更）
        $.ajax({
            url: `/inventory/update/${id}`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
			headers: {
	            "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")
	        },
            success: function(response) {
			    $row.find('.storageMethod').text(data.storageMethod);
			    $row.find('.purchaseDate').text(data.purchaseDate);
			    $row.find('.expirationDate').text(data.expirationDate);
			    $row.find('.quantity').text(data.quantity);
			    $row.find('.save-btn').addClass('d-none');
			    $row.find('.edit-btn').removeClass('d-none');
				alert("更新を保存しました");
            },
            error: function(error) {
                alert('保存に失敗しました');
            }
        });
		
		revertRowFromEditable($row);
		$row.removeClass("edit-mode");
        $row.find(".view-mode").removeClass("d-none");
        $row.find(".edit-mode").addClass("d-none");
        $row.find(".save-btn").addClass("d-none");
		$row.find('.cancel-btn').addClass('d-none');
        $row.find(".edit-btn i").removeClass("fa-xmark").addClass("fa-pen");
		$row.find('.edit-btn').removeClass('d-none');
    });
	
	function sendPostAjax(url, successMessage, data) {
        $.ajax({
            type: "POST",
            url: url,
			contentType: "application/json",
			data: data,
            headers: {
                "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content") // Spring Security用
            },
            success: function () {
                alert(successMessage);
                location.reload(); // 成功後リロードして状態反映
            },
            error: function () {
                alert("エラーが発生しました。");
            }
        });
    }

    $(".consume-btn").on("click", function () {
        const id = $(this).closest("tr").data("id");
        const url = `/inventory/consume/${id}`;
        sendPostAjax(url, "消費処理が完了しました。", null);
    });

	
	let selectedId = null;
	
	// 廃棄ボタンを押したときにIDを保持
    $(".discard-btn").on("click", function () {
        selectedId = $(this).data("id");
    });
	
	// 確認モーダルの「廃棄する」ボタン
    $("#confirmDiscardBtn").on("click", function () {
        if (!selectedId) return;
		
		const reason = $("#discardReason").val();
        const url = `/inventory/discard/${selectedId}`;
        sendPostAjax(url, "廃棄処理が完了しました。", JSON.stringify({ reason: reason }));
    });
	
	function convertRowToEditable($row) {
	    // 保存元データ
	    const name = $row.find('.name').text().trim();
	    const storage = $row.find('.storageMethod').text().trim();
	    const category = $row.find('.category').text().trim();
	    const purchaseDate = $row.find('.purchaseDate').text().trim();
	    const expirationDate = $row.find('.expirationDate').text().trim();
	    const quantity = $row.find('.quantity span:first').text().trim();
	    const unit = $row.find('.quantity span.form-text').text().trim();
		
		$row.find('.name').attr('data-original', name);
		$row.find('.storageMethod').attr('data-original', storage);
		$row.find('.category').attr('data-original', category);
		$row.find('.purchaseDate').attr('data-original', purchaseDate);
		$row.find('.expirationDate').attr('data-original', expirationDate);
		$row.find('.quantity')
		    .attr('data-original-quantity', quantity)
		    .attr('data-original-unit', unit);


	    // 編集用HTMLに差し替え
	    $row.find('.storageMethod').html(`<input type="text" class="form-control" value="${storage}">`);

	    $row.find('.purchaseDate').html(`<input type="date" class="form-control" value="${purchaseDate}">`);
	    $row.find('.expirationDate').html(`<input type="date" class="form-control" value="${expirationDate}">`);

	    $row.find('.quantity').html(`
	        <div class="d-flex align-items-center">
	            <input type="number" class="form-control me-1" value="${quantity}" min="1">
	            <span class="form-text">${unit}</span>
	        </div>
	    `);

	    // ボタン切り替え
	    $row.find('.edit-toggle i').removeClass('fa-pen').addClass('fa-xmark');
	    $row.find('.save-btn, .cancel-btn').removeClass('d-none');
	}

	function revertRowFromEditable($row) {
	    // 元の値を属性から取得
	    const name = $row.find('.name').attr('data-original');
	    const storage = $row.find('.storageMethod').attr('data-original');
	    const category = $row.find('.category').attr('data-original');
	    const purchaseDate = $row.find('.purchaseDate').attr('data-original');
	    const expirationDate = $row.find('.expirationDate').attr('data-original');
	    const quantity = $row.find('.quantity').attr('data-original-quantity');
	    const unit = $row.find('.quantity').attr('data-original-unit');

	    // 元の表示に戻す
	    $row.find('.name').html(name).attr('data-original', name);
	    $row.find('.storageMethod').html(storage).attr('data-original', storage);
	    $row.find('.category').html(category).attr('data-original', category);
	    $row.find('.purchaseDate').html(purchaseDate).attr('data-original', purchaseDate);
	    $row.find('.expirationDate').html(expirationDate).attr('data-original', expirationDate);
	    $row.find('.quantity').html(`
	        <span>${quantity}</span>
	        <span class="form-text">${unit}</span>
	    `).attr('data-original-quantity', quantity).attr('data-original-unit', unit);

	    // ボタン切り替え
	    $row.find('.edit-toggle i').removeClass('fa-xmark').addClass('fa-pen');
	    $row.find('.save-btn, .cancel-btn').addClass('d-none');
	}


});
