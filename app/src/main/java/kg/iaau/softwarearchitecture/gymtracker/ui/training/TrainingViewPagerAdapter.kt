package kg.iaau.softwarearchitecture.gymtracker.ui.training

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.iaau.softwarearchitecture.gymtracker.ui.training.all.AllTrainingFragment
import kg.iaau.softwarearchitecture.gymtracker.ui.training.man.ManTrainingFragment
import kg.iaau.softwarearchitecture.gymtracker.ui.training.woman.WomanTrainingFragment
import kg.iaau.softwarearchitecture.gymtracker.utils.ALL_PAGE
import kg.iaau.softwarearchitecture.gymtracker.utils.MAN_PAGE
import kg.iaau.softwarearchitecture.gymtracker.utils.WOMAN_PAGE

class TrainingViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    private val fragments : Map<Int, () -> Fragment> = mapOf(
        ALL_PAGE to { AllTrainingFragment() },
        MAN_PAGE to { ManTrainingFragment() },
        WOMAN_PAGE to { WomanTrainingFragment() }
    )

}