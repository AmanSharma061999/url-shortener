/* label, // label for the input field
  id, // id for the input field
  type, // type of the input field
  errors, // errors object from react-hook-form
  register, // register function from react-hook-form
  required, // boolean to indicate if the field is required
  message, // error message to display if the field is required
  className, // additional class names for styling
  min, // minimum length for the input field
  value, // value of the input field
  placeholder, // placeholder text for the input field*/

const TextField = ({
  label,
  id,
  type,
  errors,
  register,
  required,
  message,
  className,
  min,
  value,
  placeholder,
}) => {
  return (
    <div className="flex flex-col gap-1">
      <label
        htmlFor={id}
        className={`${className ? className : ""} font-semibold text-md`}
      >
        {label}
      </label>

      <input
        type={type}
        id={id}
        placeholder={placeholder}
        className={`${
          className ? className : ""
        } px-3 py-2 rounded-md outline-none
        bg-black/30 text-white placeholder:text-white/40
        border ${errors[id]?.message ? "border-red-500/80" : "border-white/15"}
        focus:border-emerald-300/60 transition`}
        {...register(id, {
          required: { value: required, message },
          minLength: min
            ? { value: min, message: "Minimum 6 character is required" }
            : null,
          pattern:
            type === "email"
              ? {
                  value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                  message: "Invalid email",
                }
              : type === "url"
              ? {
                  value:
                    /^(https?:\/\/)?(([a-zA-Z0-9\u00a1-\uffff-]+\.)+[a-zA-Z\u00a1-\uffff]{2,})(:\d{2,5})?(\/[^\s]*)?$/,
                  message: "Please enter a valid url",
                }
              : null,
        })}
      />

      {errors[id]?.message && (
        <p className="text-sm font-semibold text-red-400 mt-0">
          {errors[id]?.message}*
        </p>
      )}
    </div>
  );
};

export default TextField;
