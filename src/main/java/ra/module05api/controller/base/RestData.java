package ra.module05api.controller.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestData<T> {

    private Object status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public RestData(T data) {
        this.status = RestStatus.SUCCESS;
        this.data = data;
    }

    public RestData(RestStatus status, T message) {
        this.status = status;
        this.message = message;
    }

    public static RestData<?> error(Object message) {
        return new RestData<>(RestStatus.ERROR, message);
    }
}
