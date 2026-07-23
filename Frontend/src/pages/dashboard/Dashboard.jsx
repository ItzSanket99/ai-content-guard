import AppSidebar from "@/components/layout/AppSidebar";

export default function Dashboard() {
  return (
    <div className="flex">
      <AppSidebar />

      <main className="flex-1 p-10 bg-slate-900 text-white">
        Dashboard
      </main>
    </div>
  );
}