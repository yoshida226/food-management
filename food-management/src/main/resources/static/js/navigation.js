$(document).ready(function() {
  const currentPath = window.location.pathname;

  $('.navbar-nav .nav-link').each(function () {
    const linkPath = $(this).attr('href');

    if (currentPath === linkPath) {
      $(this).addClass('active');
    }
	
	if(currentPath.includes('report')){
		$('#reportDropdown').addClass('active');
	}
  });
});