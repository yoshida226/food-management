$(document).ready(function() {
    // 編集ボタン押したとき
	$(".edit-btn").on("click", function () {
        const $row = $(this).closest("tr");
        const startEditing = $(this).find('i').hasClass('fa-pen');
	    
	    if (startEditing) {
			convertRowToEditable($row);
	    } else {
			revertRowFromEditable($row);
	    }
		
    });
	
    // 保存ボタン押したとき
    $('.save-btn').on('click', function() {
        var $row = $(this).closest('tr');
        var id = $row.data('id');	            

        var data = {
            name: $row.find('.name input').val(),
            categoryId: $row.find('.category select option:selected').val(),
			categoryName: $row.find('.category select option:selected').text().trim(),
            unit: $row.find('.unit input').val()
        };

        // Ajaxで保存
		const url = `/food-master/update/${id}`;
		
		const successFunction = function() {
			$row.find('.name').text(data.name);
		    $row.find('.category').text(data.categoryName);
		    $row.find('.unit').text(data.unit);
		    $row.find('.save-btn').addClass('d-none');
		    $row.find('.edit-btn').removeClass('d-none');
			alert("更新を保存しました");
		}
		
		const errorFunction = function() {
			alert('保存に失敗しました');
		}
		
		sendPostAjax(url, data, successFunction, errorFunction);
		
		//編集モードから元に戻す
		revertRowFromEditable($row);
		$row.removeClass("edit-mode");
        $row.find(".view-mode").removeClass("d-none");
        $row.find(".edit-mode").addClass("d-none");
        $row.find(".save-btn").addClass("d-none");
		$row.find('.cancel-btn').addClass('d-none');
        $row.find(".edit-btn i").removeClass("fa-xmark").addClass("fa-pen");
		$row.find('.edit-btn').removeClass('d-none');
    });
	
	let selectedId = null;
	
	// 削除ボタンを押したときにIDを保持
    $(".delete-btn").on("click", function () {
        selectedId = $(this).data("id");
    });
	
	// 確認モーダルの「削除する」ボタン
    $("#confirmDeleteBtn").on("click", function () {
        if (!selectedId) return;
		
        const url = `/food-master/delete/${selectedId}`;

		const successFunction = function() {
			alert("削除に成功しました");
		}

		const errorFunction = function() {
			alert('削除に失敗しました');
		}

        sendPostAjax(url, null, successFunction, errorFunction);
    });
	
	//編集モードにする関数
	function convertRowToEditable($row) {
	    // 保存元データ
	    const name = $row.find('.name').text().trim();
	    const category = $row.find('.category').text().trim();
	    const unit = $row.find('.unit').text().trim();
		
		$row.find('.name').attr('data-original', name);
		$row.find('.category').attr('data-original', category);
		$row.find('.unit').attr('data-original', unit);

	    // 編集用HTMLに差し替え
	    $row.find('.name').html(`<input type="text" class="form-control" value="${name}">`);
		
		// カテゴリー選択用のセレクトボックスを作成
	    const categoryOptions = createCategoryOptions(category);
	    $row.find('.category').html(`<select class="form-control">${categoryOptions}</select>`);
		    
	    $row.find('.unit').html(`<input type="text" class="form-control" value="${unit}">`);


	    // ボタン切り替え
		$row.find('.edit-btn i').removeClass('fa-pen').addClass('fa-xmark');
	    $row.find('.save-btn, .cancel-btn').removeClass('d-none');
	}

	//編集モードから戻す関数
	function revertRowFromEditable($row) {
	    // 元の値を属性から取得
	    const name = $row.find('.name').attr('data-original');
	    const category = $row.find('.category').attr('data-original');
	    const unit = $row.find('.unit').attr('data-original');

	    // 元の表示に戻す
	    $row.find('.name').html(name).attr('data-original', name);
	    $row.find('.category').html(category).attr('data-original', category);
	    $row.find('.unit').html(unit).attr('data-original', unit);

	    // ボタン切り替え
		$row.find('.edit-btn i').removeClass('fa-xmark').addClass('fa-pen');
	    $row.find('.save-btn, .cancel-btn').addClass('d-none');
	}
	
	// カテゴリ選択肢を生成する関数
	function createCategoryOptions(selectedCategory) {
	    // この部分はページロード時に Thymeleaf で生成された隠し要素から取得する
	    const categories = [];
	    $('#category-data option').each(function() {
	        categories.push({
	            id: $(this).val(),
	            name: $(this).text()
	        });
	    });
	    
	    // 選択肢のHTMLを生成
	    let optionsHtml = '';
	    for (const category of categories) {
	        const selected = category.name === selectedCategory ? 'selected' : '';
	        optionsHtml += `<option value="${category.id}" ${selected}>${category.name}</option>`;
	    }
	    
	    return optionsHtml;
	}

	//Ajax通信メソッド
	function sendPostAjax(url, data, successFunction, errorFunction) {
		$.ajax({
	        url: url,
	        type: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(data),
			headers: {
	            "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")
	        },
	        success: successFunction,
	        error: errorFunction
	    });
	}

});
