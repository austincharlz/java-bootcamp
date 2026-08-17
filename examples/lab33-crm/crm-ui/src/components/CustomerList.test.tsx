import {render, screen, within} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import {describe, expect, it, vi} from 'vitest'
import {seedCustomers} from '../data/seedCustomers'
import {CustomerList} from './CustomerList'

describe('CustomerList', () => {
    it('renders cards for Amina and Ravi', () => {
        render(<CustomerList customers={seedCustomers} onEdit={() => {
        }}/>)

        expect(screen.getByRole('heading', {name: 'Amina Khan'})).toBeInTheDocument()
        expect(screen.getByRole('heading', {name: 'Ravi Singh'})).toBeInTheDocument()
        expect(screen.getAllByRole('article')).toHaveLength(2)
    })

    it('shows empty state when there are no customers', () => {
        render(<CustomerList customers={[]} onEdit={() => {
        }}/>)

        expect(screen.getByText('No customers yet')).toBeInTheDocument()
        expect(
            screen.queryByRole('heading', {name: 'Customers'}),
        ).not.toBeInTheDocument()
    })

    it('reports the selected customer on Edit click', async () => {
        const user = userEvent.setup()
        const onEdit = vi.fn()
        render(<CustomerList customers={[seedCustomers[0]]} onEdit={onEdit}/>)

        const card = screen.getByRole('article', {name: 'Amina Khan'})
        await user.click(within(card).getByRole('button', {name: 'Edit'}))

        expect(onEdit).toHaveBeenCalledWith('CUS-1001')
    })
})
