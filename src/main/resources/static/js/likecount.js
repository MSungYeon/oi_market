    function updateLikeCount(seq) {
        // 좋아요 수를 업데이트하기 위해 AJAX 요청을 보냄
        $.ajax({
            url: "/updateLikeCount",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ seq: seq }),
            success: function(response) {
                console.log("Like count updated successfully");
                // 페이지에 표시된 좋아요 수를 업데이트함
                var likeCountElement = $("#like-count-" + seq);
                var currentCount = parseInt(likeCountElement.text());
                likeCountElement.text(currentCount + 1);
            },
            error: function(xhr, status, error) {
                console.log("Error updating like count: " + error);
            }
        });
    }