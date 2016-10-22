var app = angular.module('PaginationFilter', []);

app.filter('addPagination', function() {
	return function(input, start, pageSize) {
		if (input) {
			start = +start;
			if (input.length < pageSize) {
				return input;
			}
			
			start = (start - 1) * pageSize;
			return input.slice(start, start + pageSize);
		}
		
		return [];
	}
});

app.filter('getUniqueResources', function() {
	return function(violations){
		if (violations) {
			var uniqueArray = [];
			$.each(violations, function(index, violation){
				if (uniqueArray.indexOf(violation.resourceName) === -1) {
					uniqueArray.push(violation.resourceName);
				}
			});
			return uniqueArray;
		}
	}
});

app.filter('getFileNameFromFullPath', function () {
	return function(path) {
		if (path) {
			var index = path.lastIndexOf('/');
			return path.substring(index + 1);
		}
	}
});

app.filter('camelCase', function () {
	return function(name) {
		if (name){
			return name.charAt(0).toUpperCase() + name.slice(1);
		}
	}
});