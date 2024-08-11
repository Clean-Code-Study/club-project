import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class MeetingRequestDto {

    public record Create(
        @NotNull(message = "회원 정보는 필수입니다.")
        Long memberId,

        @NotNull(message = "제목은 필수입니다.")
        String title,

        @NotNull(message = "내용은 필수입니다.")
        String content,

        @NotNull(message = "장소는 필수입니다.")
        String location,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "참가 인원은 필수입니다.")
        int joinLimit
    ) {

        public Meeting toEntity(Member member) {
            return Meeting.builder()
                .member(member)
                .title(this.title)
                .content(this.content)
                .location(this.location)
                .date(this.date)
                .joinLimit(this.joinLimit)
                .build();
        }
    }
}
