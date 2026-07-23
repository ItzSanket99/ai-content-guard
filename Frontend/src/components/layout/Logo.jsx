import { ShieldCheck } from "lucide-react";

export default function Logo() {
  return (
    <div className="flex items-center gap-3 px-2">
      <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-blue-600">
        <ShieldCheck className="h-6 w-6 text-white" />
      </div>

      <div>
        <h2 className="text-sm font-bold tracking-wide text-white">
          AI Content Guard
        </h2>

        <p className="text-xs text-slate-400">
          Content Governance Platform
        </p>
      </div>
    </div>
  );
}