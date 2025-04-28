$(document).on('click', '.edit-btn', function() {
    // 編集ボタン押したとき
    $('.edit-btn').on('click', function() {
        var $row = $(this).closest('tr');
        $row.find('.edit-btn').addClass('d-none');
        $row.find('.save-btn').removeClass('d-none');

        convertRowToEditable($row);
    });

    // 保存ボタン押したとき
    $('.save-btn').on('click', function() {
        var $row = $(this).closest('tr');
        var id = $row.data('id');

        var data = {
            id: id,
            name: $row.find('.name input').val(),
            storageMethod: $row.find('.storageMethod input').val(),
            category: $row.find('.category select').val(),
            purchaseDate: $row.find('.purchaseDate input').val(),
            expirationDate: $row.find('.expirationDate input').val(),
            quantity: $row.find('.quantity input').val()
        };

        // Ajaxで保存（ここはURLを適宜変更）
        $.ajax({
            url: '/inventory/update',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
				$row.find('.name').text(data.name);
			    $row.find('.storageMethod').text(data.storageMethod);
			    $row.find('.category').text(data.category);
			    $row.find('.purchaseDate').text(data.purchaseDate);
			    $row.find('.expirationDate').text(data.expirationDate);
			    $row.find('.quantity').text(data.quantity);
			    $row.find('.save-btn').addClass('d-none');
			    $row.find('.edit-btn').removeClass('d-none')
            },
            error: function(error) {
                alert('保存に失敗しました');
            }
        });
    });

    function convertRowToEditable($row) {
        $row.find('.name').html('<input type="text" class="form-control" value="' + $row.find('.name').text().trim() + '">');
        $row.find('.storageMethod').html('<input type="text" class="form-control" value="' + $row.find('.storageMethod').text().trim() + '">');
        
        // カテゴリーはドロップダウン
        var currentCategory = $row.find('.category').text().trim();
        $row.find('.category').html(`
            <select class="form-select">
                <option value="野菜" ${currentCategory === '野菜' ? 'selected' : ''}>野菜</option>
			    <option value="肉" ${currentCategory === '肉' ? 'selected' : ''}>肉</option>
				<option value="魚" ${currentCategory === '魚' ? 'selected' : ''}>魚</option>
                <option value="果物" ${currentCategory === '果物' ? 'selected' : ''}>乳製品</option>
				<option value="野菜" ${currentCategory === '野菜' ? 'selected' : ''}>果物</option>
				<option value="肉" ${currentCategory === '肉' ? 'selected' : ''}>穀物</option>
				<option value="魚" ${currentCategory === '魚' ? 'selected' : ''}>調味料</option>
				<option value="果物" ${currentCategory === '果物' ? 'selected' : ''}>冷凍食品</option>
				<option value="野菜" ${currentCategory === '野菜' ? 'selected' : ''}>お菓子</option>
				<option value="肉" ${currentCategory === '肉' ? 'selected' : ''}>飲料</option>
                <option value="その他" ${currentCategory === 'その他' ? 'selected' : ''}>その他</option>
            </select>
        `);

        // 日付系はカレンダー
        $row.find('.purchaseDate').html('<input type="date" class="form-control" value="' + $row.find('.purchaseDate').text().trim() + '">');
        $row.find('.expirationDate').html('<input type="date" class="form-control" value="' + $row.find('.expirationDate').text().trim() + '">');
        $row.find('.quantity').html('<input type="number" class="form-control" value="' + $row.find('.quantity').text().trim() + '" min="1">');
    }
});
