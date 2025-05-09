function loadTable(option) {
	
	// ボタンのクラス切り替え
    const button1 = document.getElementById('button1');
    const button2 = document.getElementById('button2');

    if (option === 'consumption') {
        button1.classList.remove('btn-secondary');
        button1.classList.add('btn-primary');

        button2.classList.remove('btn-primary');
        button2.classList.add('btn-secondary');
		
    } else if (option === 'waste') {
        button2.classList.remove('btn-secondary');
        button2.classList.add('btn-primary');

        button1.classList.remove('btn-primary');
        button1.classList.add('btn-secondary');
    }
	
    fetch(`/history-data?option=${option}`) // バックエンドのAPIエンドポイントを呼び出す
        .then(response => response.json())
        .then(data => {
			console.log('Received data:', data);
			
            const tableContainer = document.getElementById('table-container');
            tableContainer.innerHTML = ''; // 既存のテーブルをクリア

            if (data && data.length > 0) {
                const table = document.createElement('table');
                table.className = 'table table-striped';

                // ヘッダーの作成
                const thead = document.createElement('thead');
                const headerRow = document.createElement('tr');
                for (const key of Object.keys(data[0])) {
                    const th = document.createElement('th');
                    th.textContent = key;
                    headerRow.appendChild(th);
                }
                thead.appendChild(headerRow);
                table.appendChild(thead);

                // ボディの作成
                const tbody = document.createElement('tbody');
                for (const item of data) {
                    const bodyRow = document.createElement('tr');
                    for (const key of Object.keys(item)) {
                        const td = document.createElement('td');
                        td.textContent = item[key];
                        bodyRow.appendChild(td);
                    }
                    tbody.appendChild(bodyRow);
                }
                table.appendChild(tbody);

                tableContainer.appendChild(table);
            } else {
                tableContainer.textContent = 'データがありません。';
            }
        })
        .catch(error => {
            console.error('データの取得に失敗しました:', error);
            document.getElementById('table-container').textContent = 'データの取得に失敗しました。';
        });
}

// ページロード時にどちらかのテーブルを初期表示する場合 (例: 選択肢1)
document.addEventListener('DOMContentLoaded', () => {
    loadTable('consumption');
});