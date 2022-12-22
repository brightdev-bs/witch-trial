(function(window, $) {
    const common = {};

    function initPrototype() {

        $.fn.serializeObject = function() {
            "use strict"
            const result = {}
            const extend = function(i, element) {
                const node = result[element.name]
                const value = element.value?.trim() || null;
                if ("undefined" !== typeof node && node !== null) {
                    if ($.isArray(node)) {
                        node.push(value)
                    } else {
                        result[element.name] = [node, value]
                    }
                } else {
                    result[element.name] = value;
                }
            }

            $.each(this.serializeArray(), extend)
            return result
        }
    }

    initPrototype();

    window.common = common;

})(window, jQuery);
