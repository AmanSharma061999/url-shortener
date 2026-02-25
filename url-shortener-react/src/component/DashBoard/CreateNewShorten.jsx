import React, { useState } from "react";
import { useForm } from "react-hook-form";
import TextField from "../TextField"; 
import api from "../../api/api"; 

const CreateNewShorten = ({ setOpen, refetch }) => {
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      originalUrl: "",
      customAlias: "",
    },
    mode: "onTouched",
  });

  const createShortUrlHandler = async (data) => {
    if (loading) return;

    try {
      setLoading(true);
      const payload = {
        originalUrl: data.originalUrl?.trim(),
        customAlias: data.customAlias?.trim() || null,
      };
      await api.post("/api/urls/shorten", payload);
      reset();
      if (typeof refetch === "function") await refetch();
      setOpen(false);
    } catch (error) {
      const msg =
        error?.response?.data?.message ||
        error?.message ||
        "Failed to create short URL";
      console.error(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full">
      {/* Header row */}
      <div className="mb-4 flex items-center justify-between">
        <h2
          className="text-white text-xl font-semibold"
          id="shorten-modal-title"
        >
          Create a New Short URL
        </h2>

        <button
          type="button"
          onClick={() => setOpen(false)}
          className="rounded-lg px-2 py-1 text-white/80 transition hover:bg-white/10 hover:text-white"
          aria-label="Close"
        >
          ✕
        </button>
      </div>

      <form
        onSubmit={handleSubmit(createShortUrlHandler)}
        className="space-y-4"
      >
        <TextField
          label="Original URL"
          id="originalUrl"
          type="url"
          placeholder="https://example.com/very/long/url"
          errors={errors}
          register={register}
          required={true}
          message="Original URL is required"
          className="text-white/80"
        />

        <TextField
          label="Custom Alias (optional)"
          id="customAlias"
          type="text"
          placeholder="my-custom-link"
          errors={errors}
          register={register}
          required={false}
          message=""
          className="text-white/80"
          min={3}
        />

        {/* Actions */}
        <div className="pt-2 flex items-center justify-end gap-3">
          <button
            type="button"
            onClick={() => {
              reset();
              setOpen(false);
            }}
            className="rounded-xl bg-white/10 px-4 py-2 text-sm font-medium text-white hover:bg-white/15"
          >
            Cancel
          </button>

          <button
            type="submit"
            disabled={loading}
            className="rounded-xl bg-green-400 px-5 py-2 text-sm font-semibold text-black transition hover:bg-green-300 disabled:opacity-60"
          >
            {loading ? "Creating..." : "Shorten"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default CreateNewShorten;
