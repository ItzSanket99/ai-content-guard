import {
  LayoutDashboard,
  FileText,
  History,
  BarChart3,
  Shield,
  Settings,
} from "lucide-react";

import Logo from "./Logo";

const menuItems = [
  {
    title: "Dashboard",
    icon: LayoutDashboard,
    path: "/dashboard",
  },
  {
    title: "Generate Summary",
    icon: FileText,
    path: "/dashboard",
  },
  {
    title: "Summary History",
    icon: History,
    path: "/history",
  },
  {
    title: "Analytics",
    icon: BarChart3,
    path: "/analytics",
  },
  {
    title: "Admin",
    icon: Shield,
    path: "/admin",
  },
  {
    title: "Settings",
    icon: Settings,
    path: "/settings",
  },
];

export default function AppSidebar() {
  return (
    <aside className="flex h-screen w-72 flex-col border-r border-slate-800 bg-slate-950">

      <div className="border-b border-slate-800 p-6">
        <Logo />
      </div>

      <nav className="flex-1 p-4">

        {menuItems.map((item) => {
          const Icon = item.icon;

          return (
            <button
              key={item.title}
              className="mb-2 flex w-full items-center gap-3 rounded-lg px-4 py-3 text-slate-300 transition hover:bg-slate-900 hover:text-white"
            >
              <Icon size={20} />
              {item.title}
            </button>
          );
        })}

      </nav>

    </aside>
  );
}