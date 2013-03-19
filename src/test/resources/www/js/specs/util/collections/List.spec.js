define(['util/collections/List'],
    function(List) {

		describe("head", function() {
		
			it("is undefined for an empty list", function() {
				expect(List.empty().head()).toBeUndefined();
			});
		});
		
		describe("tail", function() {
			it ("is undefined for an empty list", function() {
				expect(List.empty().tail().isEmpty()).toBe(true);
			});
		});
    }
);
 